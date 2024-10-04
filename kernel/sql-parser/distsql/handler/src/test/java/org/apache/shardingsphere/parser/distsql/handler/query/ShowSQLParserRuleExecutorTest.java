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

package org.apache.shardingsphere.parser.distsql.handler.query;

import org.apache.shardingsphere.distsql.statement.DistSQLStatement;
import org.apache.shardingsphere.infra.merge.result.impl.local.LocalDataQueryResultRow;
import org.apache.shardingsphere.parser.config.SQLParserRuleConfiguration;
import org.apache.shardingsphere.parser.distsql.statement.queryable.ShowSQLParserRuleStatement;
import org.apache.shardingsphere.parser.rule.SQLParserRule;
import org.apache.shardingsphere.sql.parser.api.CacheOption;
import org.apache.shardingsphere.test.it.distsql.handler.engine.query.DistSQLGlobalRuleQueryExecutorTest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ShowSQLParserRuleExecutorTest extends DistSQLGlobalRuleQueryExecutorTest<SQLParserRule> {
    
    ShowSQLParserRuleExecutorTest() {
        super(SQLParserRule.class);
    }
    
    @ParameterizedTest(name = "{0}")
    @ArgumentsSource(TestCaseArgumentsProvider.class)
    void assertExecuteQuery(final String name, final SQLParserRule rule, final DistSQLStatement sqlStatement, final List<LocalDataQueryResultRow> expectedRows) throws SQLException {
        assertQueryResultRows(rule, sqlStatement, expectedRows);
    }
    
    private static class TestCaseArgumentsProvider implements ArgumentsProvider {
        
        @Override
        public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {
            return Stream.of(
                    Arguments.arguments("withCacheOption", mockRuleWithCacheOption(), new ShowSQLParserRuleStatement(),
                            Collections.singletonList(new LocalDataQueryResultRow("initialCapacity: 128, maximumSize: 1024", "initialCapacity: 2000, maximumSize: 65535"))),
                    Arguments.arguments("withoutCacheOption", mockRuleWithoutCacheOption(), new ShowSQLParserRuleStatement(), Collections.singletonList(new LocalDataQueryResultRow("", ""))));
        }
        
        private SQLParserRule mockRuleWithCacheOption() {
            SQLParserRule result = mock(SQLParserRule.class);
            when(result.getConfiguration()).thenReturn(new SQLParserRuleConfiguration(new CacheOption(128, 1024L), new CacheOption(2000, 65535L)));
            return result;
        }
        
        private SQLParserRule mockRuleWithoutCacheOption() {
            SQLParserRule result = mock(SQLParserRule.class);
            when(result.getConfiguration()).thenReturn(new SQLParserRuleConfiguration(null, null));
            return result;
        }
    }
}
