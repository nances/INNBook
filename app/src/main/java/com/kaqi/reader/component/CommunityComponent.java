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

import com.kaqi.reader.ui.activity.BookDiscussionDetailActivity;
import com.kaqi.reader.ui.activity.BookHelpDetailActivity;
import com.kaqi.reader.ui.activity.BookReviewDetailActivity;
import com.kaqi.reader.ui.fragment.BookCompleteUpdateFragment;
import com.kaqi.reader.ui.fragment.BookDiscussionFragment;
import com.kaqi.reader.ui.fragment.BookHelpFragment;
import com.kaqi.reader.ui.fragment.BookReviewFragment;
import com.kaqi.reader.ui.fragment.ClassFicationItemFragment;
import com.kaqi.reader.ui.fragment.ClassRankTopItemFragment;
import com.kaqi.reader.ui.fragment.GirlBookDiscussionFragment;

import dagger.Component;

@Component(dependencies = AppComponent.class)
public interface CommunityComponent {

    BookDiscussionFragment inject(BookDiscussionFragment fragment);

    BookDiscussionDetailActivity inject(BookDiscussionDetailActivity activity);

    BookReviewFragment inject(BookReviewFragment fragment);

    BookReviewDetailActivity inject(BookReviewDetailActivity activity);

    BookHelpFragment inject(BookHelpFragment fragment);

    BookHelpDetailActivity inject(BookHelpDetailActivity activity);

    GirlBookDiscussionFragment inject(GirlBookDiscussionFragment fragment);

    ClassFicationItemFragment inject(ClassFicationItemFragment fragment);

    ClassRankTopItemFragment inject(ClassRankTopItemFragment fragment);

    BookCompleteUpdateFragment inject(BookCompleteUpdateFragment fragment);
}
