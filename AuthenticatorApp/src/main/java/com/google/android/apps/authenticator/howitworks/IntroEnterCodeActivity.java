/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.android.apps.authenticator.howitworks;

import com.google.android.apps.authenticator.wizard.WizardPageActivity;
import com.google.android.apps.authenticator2_plus.R;

import android.os.Bundle;

import java.io.Serializable;

/**
 * The page of the "How it works" that explains that occasionally the user might need to enter a
 * verification code generated by this application. The user simply needs to click the Next button
 * to go to the next page.
 *
 * @author klyubin@google.com (Alex Klyubin)
 */
public class IntroEnterCodeActivity extends WizardPageActivity<Serializable> {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setPageContentView(R.layout.howitworks_enter_code);
    setTextViewHtmlFromResource(R.id.details, R.string.howitworks_page_enter_code_details);
  }

  @Override
  protected void onRightButtonPressed() {
    startPageActivity(IntroVerifyDeviceActivity.class);
  }
}
