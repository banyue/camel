/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.camel.component.box.internal;

import com.box.boxjavalibv2.authorization.IAuthSecureStorage;
import com.box.boxjavalibv2.dao.IAuthData;

/**
 * A caching {@link IAuthSecureStorage} that also delegates to another {@link IAuthSecureStorage}.
 */
public class CachingSecureStorage implements IAuthSecureStorage {

    private final IAuthSecureStorage secureStorage;

    private IAuthData auth;

    public CachingSecureStorage(IAuthSecureStorage secureStorage) {
        this.secureStorage = secureStorage;
    }

    @Override
    public void saveAuth(IAuthData newAuth) {
        this.auth = newAuth;
        if (secureStorage != null) {
            secureStorage.saveAuth(newAuth);
        }
    }

    @Override
    public IAuthData getAuth() {
        if (auth == null && secureStorage != null) {
            auth = secureStorage.getAuth();
        }
        return auth;
    }
}
