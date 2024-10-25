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

package org.apache.shardingsphere.data.pipeline.mysql.ingest.incremental.binlog.position;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MySQLIncrementalPositionManagerTest {
    
    private static final String LOG_FILE_NAME = "binlog-000001";
    
    private static final long LOG_POSITION = 4L;
    
    @Mock(extraInterfaces = AutoCloseable.class)
    private DataSource dataSource;
    
    @Mock
    private Connection connection;
    
    @BeforeEach
    void setUp() throws SQLException {
        when(dataSource.getConnection()).thenReturn(connection);
        PreparedStatement positionStatement = mockPositionStatement();
        when(connection.prepareStatement("SHOW MASTER STATUS")).thenReturn(positionStatement);
    }
    
    @Test
    void assertGetCurrentPosition() throws SQLException {
        MySQLIncrementalPositionManager incrementalPositionManager = new MySQLIncrementalPositionManager();
        MySQLBinlogPosition actual = incrementalPositionManager.init(dataSource, "");
        assertThat(actual.getFilename(), is(LOG_FILE_NAME));
        assertThat(actual.getPosition(), is(LOG_POSITION));
    }
    
    private PreparedStatement mockPositionStatement() throws SQLException {
        PreparedStatement result = mock(PreparedStatement.class);
        ResultSet resultSet = mock(ResultSet.class);
        when(result.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getString(1)).thenReturn(LOG_FILE_NAME);
        when(resultSet.getLong(2)).thenReturn(LOG_POSITION);
        return result;
    }
}
