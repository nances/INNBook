/**
 * Copyright 2016 JustWayward Team
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kaqi.reader.component;

import com.kaqi.reader.ui.activity.AboutActivity;
import com.kaqi.reader.ui.activity.MoneyDetailedListActivity;
import com.kaqi.reader.ui.activity.ReadBookHistoryActivity;
import com.kaqi.reader.ui.fragment.HomeFragment;
import com.kaqi.reader.ui.activity.SettingActivity;
import com.kaqi.reader.ui.activity.WifiBookActivity;
import com.kaqi.reader.ui.fragment.RecommendFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface MainComponent {
    HomeFragment inject(HomeFragment activity);

    RecommendFragment inject(RecommendFragment fragment);

    SettingActivity inject(SettingActivity activity);

    WifiBookActivity inject(WifiBookActivity activity);

    AboutActivity inject(AboutActivity activity);

    ReadBookHistoryActivity inject(ReadBookHistoryActivity activity);

    MoneyDetailedListActivity inject(MoneyDetailedListActivity activity);
}