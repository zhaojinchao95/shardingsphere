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

grammar RLStatement;

import BaseRule;

change
    : changeMasterTo | changeReplicationFilter
    ;

changeMasterTo
    : CHANGE MASTER TO masterDefs  channelOption?
    ;

changeReplicationFilter
    : CHANGE REPLICATION FILTER filterDefs channelOption?
    ;

changeReplicationSourceTo
    : CHANGE REPLICATION SOURCE TO changeReplicationSourceOptionDefs channelOption?
    ;

startSlave
    : START SLAVE threadTypes? utilOption? connectionOptions channelOption?
    ;

stopSlave
    : STOP SLAVE threadTypes channelOption*
    ;

startReplica
    : START REPLICA threadTypes? utilOption? connectionOptions? channelOption?
    ;

groupReplication
    : startGroupReplication | stopGroupReplication
    ;

startGroupReplication
    : START GROUP_REPLICATION
    ;

stopGroupReplication
    : STOP GROUP_REPLICATION
    ;

purgeBinaryLog
    : PURGE (BINARY | MASTER) LOGS (TO logName | BEFORE datetimeExpr)
    ;

threadTypes
    : threadType+
    ;

threadType
    : RELAY_THREAD | SQL_THREAD
    ;

utilOption
    : UNTIL ((SQL_BEFORE_GTIDS | SQL_AFTER_GTIDS) EQ_ identifier
    | MASTER_LOG_FILE EQ_ string_ COMMA_ MASTER_LOG_POS EQ_ NUMBER_
    | RELAY_LOG_FILE EQ_ string_ COMMA_ RELAY_LOG_POS  EQ_ NUMBER_
    | SQL_AFTER_MTS_GAPS)
    ;

connectionOptions
    : (USER EQ_ string_)? (PASSWORD EQ_ string_)? (DEFAULT_AUTH EQ_ string_)? (PLUGIN_DIR EQ_ string_)?
    ;

masterDefs
    : masterDef (COMMA_ masterDef)*
    ;

masterDef
    : MASTER_BIND EQ_ string_
    | MASTER_HOST EQ_ string_
    | MASTER_USER EQ_ string_
    | MASTER_PASSWORD EQ_ string_
    | MASTER_PORT EQ_ NUMBER_
    | PRIVILEGE_CHECKS_USER EQ_ (ACCOUNT | NULL)
    | REQUIRE_ROW_FORMAT EQ_ NUMBER_
    | MASTER_CONNECT_RETRY EQ_ NUMBER_
    | MASTER_RETRY_COUNT EQ_ NUMBER_
    | MASTER_DELAY EQ_ NUMBER_
    | MASTER_HEARTBEAT_PERIOD EQ_ NUMBER_
    | MASTER_LOG_FILE EQ_ string_
    | MASTER_LOG_POS EQ_ NUMBER_
    | MASTER_AUTO_POSITION EQ_ NUMBER_
    | RELAY_LOG_FILE EQ_ string_
    | RELAY_LOG_POS EQ_ NUMBER_
    | MASTER_COMPRESSION_ALGORITHM EQ_ string_
    | MASTER_ZSTD_COMPRESSION_LEVEL EQ_ NUMBER_
    | MASTER_SSL EQ_ NUMBER_
    | MASTER_SSL_CA EQ_ string_
    | MASTER_SSL_CAPATH EQ_ string_
    | MASTER_SSL_CERT EQ_ string_
    | MASTER_SSL_CRL EQ_ string_
    | MASTER_SSL_CRLPATH EQ_ string_
    | MASTER_SSL_KEY EQ_ string_
    | MASTER_SSL_CIPHER EQ_ string_
    | MASTER_SSL_VERIFY_SERVER_CERT EQ_ NUMBER_
    | MASTER_TLS_VERSION EQ_ string_
    | MASTER_TLS_CIPHERSUITES EQ_ string_
    | MASTER_PUBLIC_KEY_PATH EQ_ string_
    | GET_MASTER_PUBLIC_KEY EQ_ NUMBER_
    | IGNORE_SERVER_IDS EQ_ LP_ ignoreServerIds? RP_
    ;

ignoreServerIds
    : ignoreServerId (COMMA_ ignoreServerId)
    ;

ignoreServerId
    : NUMBER_
    ;

filterDefs
    : filterDef (COMMA_ filterDef)*
    ;

filterDef
    : REPLICATE_DO_DB EQ_ LP_ databaseNames? RP_
    | REPLICATE_IGNORE_DB EQ_ LP_ databaseNames? RP_
    | REPLICATE_DO_TABLE EQ_ LP_ tableList? RP_
    | REPLICATE_IGNORE_TABLE EQ_ LP_ tableList? RP_
    | REPLICATE_WILD_DO_TABLE EQ_ LP_ wildTables? RP_
    | REPLICATE_WILD_IGNORE_TABLE EQ_ LP_ wildTables? RP_
    | REPLICATE_REWRITE_DB EQ_ LP_ databasePairs? RP_
    ;

wildTables
    : wildTable (COMMA_ wildTable)*
    ;

wildTable
    : string_
    ;

changeReplicationSourceOptionDefs
    : changeReplicationSourceOption (COMMA_ changeReplicationSourceOption)*
    ;

changeReplicationSourceOption
    : SOURCE_BIND EQ_ string_
    | SOURCE_HOST EQ_ string_
    | SOURCE_USER EQ_ string_
    | SOURCE_PASSWORD EQ_ string_
    | SOURCE_PORT EQ_ NUMBER_
    | PRIVILEGE_CHECKS_USER EQ_ (NULL | ACCOUNT)
    | REQUIRE_ROW_FORMAT EQ_ NUMBER_
    | REQUIRE_TABLE_PRIMARY_KEY_CHECK EQ_ tablePrimaryKeyCheckDef
    | ASSIGN_GTIDS_TO_ANONYMOUS_TRANSACTIONS EQ_ assignGtidsToAnonymousTransactionsDef
    | SOURCE_LOG_FILE EQ_ string_
    | SOURCE_LOG_POS EQ_ NUMBER_
    | SOURCE_AUTO_POSITION EQ_ NUMBER_
    | RELAY_LOG_FILE EQ_ string_
    | RELAY_LOG_POS EQ_ NUMBER_
    | SOURCE_HEARTBEAT_PERIOD EQ_ NUMBER_
    | SOURCE_CONNECT_RETRY EQ_ NUMBER_
    | SOURCE_RETRY_COUNT EQ_ NUMBER_
    | SOURCE_CONNECTION_AUTO_FAILOVER EQ_ NUMBER_
    | SOURCE_DELAY EQ_ NUMBER_
    | SOURCE_COMPRESSION_ALGORITHMS EQ_ string_
    | SOURCE_ZSTD_COMPRESSION_LEVEL EQ_ NUMBER_
    | SOURCE_SSL EQ_ NUMBER_
    | SOURCE_SSL_CA EQ_ string_
    | SOURCE_SSL_CAPATH EQ_ string_
    | SOURCE_SSL_CERT EQ_ string_
    | SOURCE_SSL_CRL EQ_ string_
    | SOURCE_SSL_CRLPATH EQ_ string_
    | SOURCE_SSL_KEY EQ_ string_
    | SOURCE_SSL_CIPHER EQ_ string_
    | SOURCE_SSL_VERIFY_SERVER_CERT EQ_ NUMBER_
    | SOURCE_TLS_VERSION EQ_ string_
    | SOURCE_TLS_CIPHERSUITES EQ_ string_
    | SOURCE_PUBLIC_KEY_PATH EQ_ string_
    | GET_SOURCE_PUBLIC_KEY EQ_ NUMBER_
    | NETWORK_NAMESPACE EQ_ string_
    | IGNORE_SERVER_IDS EQ_ LP_ ignoreServerIds? RP_
    | GTID_ONLY EQ_ NUMBER_
    ;

tablePrimaryKeyCheckDef
    : (STREAM | ON | OFF | GENERATE)
    ;

assignGtidsToAnonymousTransactionsDef
    : (OFF | LOCAL | string_)
    ;
