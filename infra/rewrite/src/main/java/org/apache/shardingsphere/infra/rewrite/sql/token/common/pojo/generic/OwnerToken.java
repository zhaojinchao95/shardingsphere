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

package org.apache.shardingsphere.infra.rewrite.sql.token.common.pojo.generic;

import com.google.common.base.Strings;
import lombok.Getter;
import org.apache.shardingsphere.infra.rewrite.sql.token.common.pojo.RouteUnitAware;
import org.apache.shardingsphere.infra.rewrite.sql.token.common.pojo.SQLToken;
import org.apache.shardingsphere.infra.rewrite.sql.token.common.pojo.Substitutable;
import org.apache.shardingsphere.infra.route.context.RouteUnit;
import org.apache.shardingsphere.sql.parser.statement.core.value.identifier.IdentifierValue;

import java.util.Set;

/**
 * Owner token.
 */
public final class OwnerToken extends SQLToken implements Substitutable, RouteUnitAware {
    
    @Getter
    private final int stopIndex;
    
    private final IdentifierValue ownerName;
    
    private final IdentifierValue tableName;
    
    public OwnerToken(final int startIndex, final int stopIndex, final IdentifierValue ownerName, final IdentifierValue tableName) {
        super(startIndex);
        this.stopIndex = stopIndex;
        this.ownerName = ownerName;
        this.tableName = tableName;
    }
    
    @Override
    public String toString(final RouteUnit routeUnit) {
        if (null != ownerName && !Strings.isNullOrEmpty(ownerName.getValue()) && tableName.getValue().equals(ownerName.getValue())) {
            Set<String> actualTableNames = routeUnit.getActualTableNames(tableName.getValue());
            String actualTableName = actualTableNames.isEmpty() ? tableName.getValue().toLowerCase() : actualTableNames.iterator().next();
            return tableName.getQuoteCharacter().wrap(actualTableName) + ".";
        }
        return toString();
    }
    
    @Override
    public String toString() {
        return null == ownerName || Strings.isNullOrEmpty(ownerName.getValue()) ? "" : ownerName.getValueWithQuoteCharacters() + ".";
    }
}
