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

package org.apache.shardingsphere.sql.parser.statement.core.segment.generic.bound;

import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.sql.parser.statement.core.value.identifier.IdentifierValue;

/**
 * Column segment bound info.
 */
@RequiredArgsConstructor
public final class ColumnSegmentBoundInfo {
    
    private final IdentifierValue originalDatabase;
    
    private final IdentifierValue originalSchema;
    
    private final IdentifierValue originalTable;
    
    private final IdentifierValue originalColumn;
    
    public ColumnSegmentBoundInfo(final IdentifierValue originalColumn) {
        originalDatabase = new IdentifierValue("");
        originalSchema = new IdentifierValue("");
        originalTable = new IdentifierValue("");
        this.originalColumn = originalColumn;
    }
    
    /**
     * Get original database.
     *
     * @return original database
     */
    public IdentifierValue getOriginalDatabase() {
        return null == originalDatabase ? new IdentifierValue("") : originalDatabase;
    }
    
    /**
     * Get original schema.
     *
     * @return original schema
     */
    public IdentifierValue getOriginalSchema() {
        return null == originalSchema ? new IdentifierValue("") : originalSchema;
    }
    
    /**
     * Get original table.
     *
     * @return original table
     */
    public IdentifierValue getOriginalTable() {
        return null == originalTable ? new IdentifierValue("") : originalTable;
    }
    
    /**
     * Get original column.
     *
     * @return original column
     */
    public IdentifierValue getOriginalColumn() {
        return null == originalColumn ? new IdentifierValue("") : originalColumn;
    }
}
