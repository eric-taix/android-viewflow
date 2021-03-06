# View Flow for Android

ViewFlow is an Android UI widget providing a horizontally scrollable [ViewGroup](http://developer.android.com/reference/android/view/ViewGroup.html) with items populated from an [Adapter](http://developer.android.com/reference/android/widget/Adapter.html). Scroll down to the bottom of the page for a screen shot.

The component is a [Library Project](http://developer.android.com/guide/developing/eclipse-adt.html#libraryProject). This means that there's no need to copy-paste resources into your own project, simply add the viewflow component as a reference to any project.

## Usage

### In your layout

    <org.taptwo.android.widget.ViewFlow
	    android:id="@+id/viewflow"
	    app:sidebuffer="5"
        />

The use of `app:sidebuffer` is optional. It defines the number of Views to buffer on each side of the currently shown View. The default sidebuffer is 3, making up a grand total of 7 (3 * 2 + 1) Views loaded at a time (at max).
To be able to use the more convenient `app:title` the application namespace must be included in the same manner as the android namespace is. Please refer to the layout main.xml in the example project for a full example. Again, note that it's the application namespace and *not* the viewflow namespace that must be referred like `xmlns:app="http://schemas.android.com/apk/res/your.application.package.here"`.

### In your activity

    ViewFlow viewFlow = (ViewFlow) findViewById(R.id.viewflow);
    viewFlow.setAdapter(myAdapter);

### Listen on screen change events

If you need to listen to screen change events you would want to implement your own `ViewFlow.ViewSwitchListener` and pass it to the `setOnViewSwitchListener()` method.

    viewFlow.setOnViewSwitchListener(new ViewSwitchListener() {
        public void onSwitched(View v) {
            // Your code here
        }
    });

### Flow Indicator
It is also possible to add a flow view indicator to your layout. The purpose of a `FlowIndicator` is to present a visual representation of where in the item list focus is at. You may either implement a `FlowIndicator` yourself or use an implementation provided by the View Flow library. Like so:

	<org.taptwo.android.widget.CircleFlowIndicator
		android:padding="10dip" android:layout_height="wrap_content"
		android:layout_width="wrap_content" android:id="@+id/viewflowindic"
		android:background="#00000000"/>

And then you'll need to connect your `ViewFlow` with the `FlowIndicator`:

	CircleFlowIndicator indic = (CircleFlowIndicator) findViewById(R.id.viewflowindic);
	viewFlow.setFlowIndicator(indic);

## Contributions

* Eric Taix
* Marc Reichelt, <http://marcreichelt.blogspot.com/>

## License
Copyright (c) 2011 [Patrik Åkerfeldt](http://about.me/pakerfeldt)

Licensed under the [Apache License, Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)

![ViewFlow for Android](https://github.com/pakerfeldt/android-viewflow/raw/master/viewflow-example/screen.png "ViewFlow for Android")


