# speech-to-text
An android plugin that exposes speech-to-text functionality to Unity

A couple reminders:
-Make sure whatever app is using this plug has permissions enabled for microphone.

Here is an example of working code in C# via Unity:

public class UnityAndroidInterface : MonoBehaviour {
	private string gameObjectName;
	AndroidJavaClass _class;
	AndroidJavaObject instance { get { return _class.GetStatic<AndroidJavaObject>("instance"); } }

	void Awake(){
		this.gameObjectName = gameObject.name;
		Setup();
	}

	private void Setup()
	{
		// Start plugin `Fragment`.
		_class = new AndroidJavaClass("com.speech2text.plugins.Speech2Text");
		_class.CallStatic("start", gameObjectName);
	}

	public void startRecordingSpeech()
	{
        Debug.Log("Starting recording");
        instance.Call("startRecordingSpeech");
    }

    public void androidSpeechCallback(string result){
		Debug.Log(result);
	}
}
