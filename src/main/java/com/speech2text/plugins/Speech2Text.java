//
// Copyright (c) 2016 eppz! mobile, Gergely Borbás (SP)
//
// http://www.twitter.com/_eppz
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
// INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A
// PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
// HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF
// CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
// OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
//

package com.speech2text.plugins;

// Features.
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

// Unity.
import com.unity3d.player.UnityPlayer;

// Debug.
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;


public class Speech2Text extends Fragment
{
    // Singleton instance.
    public static Speech2Text instance;

    // Unity context.
    String gameObjectName;

    public static SpeechRecognizer sr;
    public static final String TAG = "Speech2TextAndroid";
    private static final String CALLBACK_METHOD_NAME = "androidSpeechCallback";
    private final Intent intent;

    public Speech2Text(){
        intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,"voice.recognition.test");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,25);
    }

    public static void start(String gameObjectName)
    {
        // Instantiate and add to Unity Player Activity.
        instance = new Speech2Text();
        instance.gameObjectName = gameObjectName; // Store `GameObject` reference
        UnityPlayer.currentActivity.getFragmentManager().beginTransaction().add(instance, Speech2Text.TAG).commit();
        setSpeechRecognizer();
        Log.d(TAG, "We started ok :)");
    }

    private static void setSpeechRecognizer(){
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){
            public void run(){
                if(sr == null) {
                    sr = SpeechRecognizer.createSpeechRecognizer(UnityPlayer.currentActivity);
                    sr.setRecognitionListener(new Speech2TextListener());
                }
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); // Retain between configuration changes (like device rotation)
    }

    public void startRecordingSpeech() {
        UnityPlayer.currentActivity.runOnUiThread(new Runnable(){
            public void run(){
                sr.startListening(intent);
            }
        });
    }

    public void collectAndSendTextResult(String text)
    {
        // Call back to Unity.
        UnityPlayer.UnitySendMessage(gameObjectName, CALLBACK_METHOD_NAME, text);
    }
}