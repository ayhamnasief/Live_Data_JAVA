**ViewModel** is a class that is responsible for preparing and managing the data for an activity or a fragment, It also handles the communication of the activity or fragment with the rest of the application. 
The ViewModel is always created in association with a scope, a fragment, or an activity and will be retained, It will be preserved as long as the scope is alive For example, if it is an activity until it is finished, In other words, this means that a ViewModel will not be destroyed if its owner is destroyed for a configuration change and the configuration change, maybe the rotation of the screen.

**Example (KOTLIN)**
```java
// ViewModel
implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
// LiveData
implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
```
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tvCounter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btnIncrease"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvCounter" />
</androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
```kotlin
class CounterViewModel:ViewModel() {
    private var initCounter:Int = 0

    fun increaseCounter(){
        initCounter ++;
    }

    fun getCounter():Int{
        return initCounter
    }
}
```
```kotlin
class MainActivity : AppCompatActivity() {
lateinit var binding:ActivityMainBinding
lateinit var counterViewModel: CounterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        counterViewModel = ViewModelProvider(this).get(CounterViewModel::class.java)

        binding.tvCounter.text = counterViewModel.getCounter().toString()

        binding.btnIncrease.setOnClickListener {
            counterViewModel.increaseCounter()
            binding.tvCounter.text = counterViewModel.getCounter().toString()
        }
    }
}
```

**Example (JAVA)**
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <TextView
        android:id="@+id/tvCounter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvCounter" />
</androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
```java
public class CounterViewModel extends ViewModel {
    int counter = 0;

    public void increaseCounter(){
        counter++;
    }

    public int getCounter(){
        return counter;
    }
}
```
```java
public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
CounterViewModel counterViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        counterViewModel = new ViewModelProvider(this).get(CounterViewModel.class);

        binding.tvCounter.setText(String.valueOf(counterViewModel.getCounter()));

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterViewModel.increaseCounter();
                binding.tvCounter.setText(String.valueOf(counterViewModel.getCounter()));
            }
        });
    }
}
```

**Live Data** is lifecycle aware, meaning it respects the lifecycle of other application components such as activities, fragments or services.
This awareness ensures LiveData only updates app components observers that are in an active lifecycle state.
If the lifecycle state is started or resumed, live data considers an observer to be in an active state.
Only active observers are notified of lifecycle updates by LiveData.
It eliminates memory leaks caused by multiple callbacks that send results to the UI thread, ensuring that the UI is always up to date.
It decouples tight integration between data mediator and the UI to avoid crashed activities.
UI components continuously monitor relevant data, and LiveData manages all these tasks automatically as the relevant lifecycle status changes.
If the activity or fragment is recreated due to configuration changes such as a device, rotation, portrait or landscape, it immediately receives the latest information from LiveData.
Extended LiveData wraps system services so that they can be shared within the app.

**Example (KOTLIN)**
```kotlin
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <data>
        <variable
            name="myCounterViewModel"
            type="com.example.vm_livedata_kotlin.CounterViewModel" />
    </data>
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

    <TextView
        android:id="@+id/tvCounter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{myCounterViewModel.counter.toString()}"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btnIncrease"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:onClick="@{myCounterViewModel::increaseCounter}"
        android:text="Button"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvCounter" />
</androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
```kotlin
class CounterViewModel:ViewModel() {
    var counter = MutableLiveData<Int>()

    init {
        counter.value = 0
    }
    fun increaseCounter(view: View){
        counter.value = (counter.value)?.plus(1)
    }
}
```
```kotlin
class MainActivity : AppCompatActivity() {
lateinit var binding:ActivityMainBinding
lateinit var counterViewModel: CounterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        counterViewModel = ViewModelProvider(this).get(CounterViewModel::class.java)
        binding.lifecycleOwner = this // Very Important
        binding.myCounterViewModel = counterViewModel
    }
}
```

**Example (JAVA)**
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <data>

    </data>
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <TextView
        android:id="@+id/tvCounter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="TextView"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvCounter" />
</androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
```java
public class CounterViewModel extends ViewModel {
    MutableLiveData<Integer> counter = new MutableLiveData<Integer>();

    public void increaseCounter(){
        int currentCounter = counter.getValue() !=null ? counter.getValue() : 0;
        counter.setValue(currentCounter+1);
    }

    public LiveData<Integer> getCounter(){
        return counter;
    }
}
```
```java
public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
CounterViewModel counterViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        counterViewModel = new ViewModelProvider(this).get(CounterViewModel.class);

        binding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterViewModel.increaseCounter();
            }
        });

        counterViewModel.getCounter().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvCounter.setText(integer+"");
            }
        });
    }
}
```

**Example for Live Data + Data Binding - JAVA**
```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools">
    <data>
        <variable
            name="counterViewModel"
            type="com.example.viewmodel_java.CounterViewModel" />
    </data>
<androidx.constraintlayout.widget.ConstraintLayout
    android:id="@+id/main"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">


    <TextView
        android:id="@+id/tvCounter"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="@{counterViewModel.counter.toString()}"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <Button
        android:id="@+id/btn"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Button"
        android:onClick="@{counterViewModel::increaseCounter}"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/tvCounter" />
</androidx.constraintlayout.widget.ConstraintLayout>
</layout>
```
```java
public class CounterViewModel extends ViewModel {
    MutableLiveData<Integer> counter = new MutableLiveData<Integer>();

    public void increaseCounter(View view){
        int currentCounter = counter.getValue() !=null ? counter.getValue() : 0;
        counter.setValue(currentCounter+1);
    }

    public LiveData<Integer> getCounter(){
        return counter;
    }
}
```
```java
public class MainActivity extends AppCompatActivity {
ActivityMainBinding binding;
CounterViewModel counterViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        counterViewModel = new ViewModelProvider(this).get(CounterViewModel.class);

        binding.setCounterViewModel(counterViewModel);

        counterViewModel.getCounter().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                binding.tvCounter.setText(integer+"");
            }
        });
    }
}
```
