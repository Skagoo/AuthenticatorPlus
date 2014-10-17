/*
 * Copyright 2014 Richard Banasiak. All Rights Reserved.
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

package com.google.android.apps.authenticator.backup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.apps.authenticator.dataexport.Exporter;
import com.google.android.apps.authenticator.testability.DependencyInjector;
import com.google.android.apps.authenticator2_plus.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class BackupActivity extends Activity implements View.OnClickListener{

    private static final String TAG = BackupActivity.class.getSimpleName();

    private static final String BACKUP_FILE = "authenticator.json";

    public static final String BACKUP_FILE_STRING = new File(Environment.getExternalStorageDirectory(), BACKUP_FILE).getAbsolutePath();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.backup);

        TextView backupText = (TextView) findViewById(R.id.backup_text);
        backupText.setText(getString(R.string.backup_text, BACKUP_FILE_STRING));

        TextView restoreText = (TextView) findViewById(R.id.restore_text);
        restoreText.setText(getString(R.string.restore_text, BACKUP_FILE_STRING));

        Button backupButton = (Button) findViewById(R.id.backup);
        backupButton.setOnClickListener(this);

        Button restoreButton = (Button) findViewById(R.id.restore);
        restoreButton.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
        switch(v.getId()) {
            case R.id.backup:
                onBackupButtonPressed();
                break;
            case R.id.restore:
                onRestoreButtonPressed();
                break;
        }
    }

    private void onBackupButtonPressed() {
        Exporter exporter = new Exporter(DependencyInjector.getAccountDb(), null);
        Bundle bundle = exporter.getData();

        JSONObject json = exporter.getJsonData();
        if(writeJsonToFile(json)) {
            Toast.makeText(this, "Backup successful", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Backup failed", Toast.LENGTH_SHORT).show();
        }

    }

    private void onRestoreButtonPressed() {

    }

    private boolean writeJsonToFile(JSONObject json) {
        File file = new File(BACKUP_FILE_STRING);
        Log.d(TAG, "backup file = " + file.getAbsolutePath());
        try {
            FileOutputStream stream = new FileOutputStream(file, false);
            stream.write(json.toString().getBytes());
            stream.close();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Unable to write backup to external storage");
            Log.e(TAG, e.getMessage());
        }
        return false;
    }

    private JSONObject readJsonFromFile() {
        File file = new File(BACKUP_FILE_STRING);
        Log.d(TAG, "restore file = " + file.getAbsolutePath());
        int length = (int) file.length();
        byte[] bytes = new byte[length];

        JSONObject json = null;
        try {
            FileInputStream in = new FileInputStream(file);
            in.read(bytes);
            in.close();
            json = new JSONObject(new String(bytes));

        } catch (Exception e) {
            Log.e(TAG, "Unable to read backup from external storage");
        }

        return json;
    }


}
