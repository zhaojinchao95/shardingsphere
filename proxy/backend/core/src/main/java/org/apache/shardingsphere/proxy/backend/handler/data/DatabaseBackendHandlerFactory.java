/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.proxy.backend.handler.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.apache.shardingsphere.infra.binder.context.statement.SQLStatementContext;
import org.apache.shardingsphere.infra.session.query.QueryContext;
import org.apache.shardingsphere.proxy.backend.connector.DatabaseConnectorFactory;
import org.apache.shardingsphere.proxy.backend.handler.data.impl.UnicastDatabaseBackendHandler;
import org.apache.shardingsphere.proxy.backend.response.header.update.UpdateResponseHeader;
import org.apache.shardingsphere.proxy.backend.session.ConnectionSession;
import org.apache.shardingsphere.sql.parser.statement.core.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.attribute.type.DatabaseSelectRequiredSQLStatementAttribute;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dal.DALStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dal.SetStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dml.DoStatement;
import org.apache.shardingsphere.sql.parser.statement.core.statement.type.dml.SelectStatement;

/**
 * Database backend handler factory.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class DatabaseBackendHandlerFactory {
    
    /**
     * New instance of database backend handler.
     *
     * @param queryContext query context
     * @param connectionSession connection session
     * @param preferPreparedStatement use prepared statement as possible
     * @return created instance
     */
    public static DatabaseBackendHandler newInstance(final QueryContext queryContext, final ConnectionSession connectionSession, final boolean preferPreparedStatement) {
        SQLStatementContext sqlStatementContext = queryContext.getSqlStatementContext();
        SQLStatement sqlStatement = sqlStatementContext.getSqlStatement();
        if (sqlStatement instanceof DoStatement) {
            return new UnicastDatabaseBackendHandler(queryContext, connectionSession);
        }
        if (sqlStatement instanceof SetStatement && null == connectionSession.getUsedDatabaseName()) {
            return () -> new UpdateResponseHeader(sqlStatement);
        }
        if (isNotDatabaseSelectRequiredDALStatement(sqlStatement) || isNotContainFromSelectStatement(sqlStatement)) {
            return new UnicastDatabaseBackendHandler(queryContext, connectionSession);
        }
        return DatabaseConnectorFactory.getInstance().newInstance(queryContext, connectionSession.getDatabaseConnectionManager(), preferPreparedStatement);
    }
    
    private static boolean isNotDatabaseSelectRequiredDALStatement(final SQLStatement sqlStatement) {
        return sqlStatement instanceof DALStatement && !sqlStatement.getAttributes().findAttribute(DatabaseSelectRequiredSQLStatementAttribute.class).isPresent();
    }
    
    private static boolean isNotContainFromSelectStatement(final SQLStatement sqlStatement) {
        return sqlStatement instanceof SelectStatement && !((SelectStatement) sqlStatement).getFrom().isPresent();
    }
}
