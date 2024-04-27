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

package org.apache.shardingsphere.parser.yaml.swapper;

import org.apache.shardingsphere.parser.config.SQLParserRuleConfiguration;
import org.apache.shardingsphere.parser.rule.builder.DefaultSQLParserRuleConfigurationBuilder;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class YamlSQLParserDataNodeRuleConfigurationSwapperTest {
    
    private final YamlSQLParserDataNodeRuleConfigurationSwapper swapper = new YamlSQLParserDataNodeRuleConfigurationSwapper();
    
    @Test
    void assertSwapToDataNodes() {
        assertThat(swapper.swapToRepositoryTuples(new SQLParserRuleConfiguration(DefaultSQLParserRuleConfigurationBuilder.PARSE_TREE_CACHE_OPTION,
                DefaultSQLParserRuleConfigurationBuilder.SQL_STATEMENT_CACHE_OPTION)).iterator().next().getKey(), is("sql_parser"));
    }
}
