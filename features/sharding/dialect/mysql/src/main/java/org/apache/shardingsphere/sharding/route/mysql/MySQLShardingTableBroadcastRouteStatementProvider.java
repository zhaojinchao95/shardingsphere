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

package org.apache.shardingsphere.sharding.route.mysql;

import org.apache.shardingsphere.sharding.route.engine.type.broadcast.DialectShardingTableBroadcastRouteStatementProvider;
import org.apache.shardingsphere.sql.parser.statement.core.statement.SQLStatement;
import org.apache.shardingsphere.sql.parser.statement.mysql.dal.MySQLOptimizeTableStatement;

import java.util.Collection;
import java.util.Collections;

/**
 * Sharding table broadcast route statement provider for MySQL.
 */
public final class MySQLShardingTableBroadcastRouteStatementProvider implements DialectShardingTableBroadcastRouteStatementProvider {
    
    @Override
    public Collection<Class<? extends SQLStatement>> getBroadcastRouteStatementTypes() {
        return Collections.singleton(MySQLOptimizeTableStatement.class);
    }
    
    @Override
    public String getDatabaseType() {
        return "MySQL";
    }
}
