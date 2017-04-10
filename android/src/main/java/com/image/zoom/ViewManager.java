package com.image.zoom;

import android.util.Log;
import android.widget.ImageView.ScaleType;

import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.annotations.ReactProp;

import java.util.Map;

import javax.annotation.Nullable;


/**
 * Created by azou on 15/02/16.
 */
public class ViewManager extends SimpleViewManager<PhotoViewWrapper> {

    public static final int COMMAND_RESET_SCALE = 1;
    
    @Override
    public String getName() {
        return "ImageViewZoom";
    }

    @Override
    public PhotoViewWrapper createViewInstance(ThemedReactContext reactContext) {
        return new PhotoViewWrapper(reactContext);
    }

    @ReactProp(name = "src")
    public void setSource(final PhotoViewWrapper view, ReadableMap params) {
        view.setSource(params);
    }
    
    @Override
    public Map<String,Integer> getCommandsMap() {
        Log.d("React"," View manager getCommandsMap:");
        return MapBuilder.of("setImageScale",
                             COMMAND_SET_SCALE);
    }
    
    @Override
    public void receiveCommand(PhotoViewWrapper view,
                               int commandType,
                               @Nullable ReadableArray args) {
        Assertions.assertNotNull(view);
        Assertions.assertNotNull(args);
        switch (commandType) {
            case COMMAND_RESET_SCALE: {
                view.setCustomScale(1);
                return;
            }
            default:
                throw new IllegalArgumentException(String.format(
                                                                 "Unsupported command %d received by %s.",
                                                                 commandType,
                                                                 getClass().getSimpleName()));
        }
    }

    @Override
    public @Nullable
    Map getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.of(
            ImageEvent.eventNameForType(ImageEvent.ON_TAP), MapBuilder.of("registrationName", "onTap"),
            ImageEvent.eventNameForType(ImageEvent.ON_LOAD), MapBuilder.of("registrationName", "onLoad"),
            ImageEvent.eventNameForType(ImageEvent.ON_SCALE), MapBuilder.of("registrationName", "onScaleChange"),
            ImageEvent.eventNameForType(ImageEvent.ON_MATRIX), MapBuilder.of("registrationName", "onMatrixChange")
        );
    }

    @Override
 public RSSignatureCaptureMainView createViewInstance(ThemedReactContext context) {
  Log.d("React"," View manager createViewInstance:");
  return new RSSignatureCaptureMainView(context, mCurrentActivity);
 }

 @Override
 public Map<String,Integer> getCommandsMap() {
  Log.d("React"," View manager getCommandsMap:");
  return MapBuilder.of(
    "saveImage",
    COMMAND_SAVE_IMAGE,
    "resetImage",
    COMMAND_RESET_IMAGE);
 }

    @ReactProp(name = "tintColor", customType = "Color")
    public void setTintColor(PhotoViewWrapper view, @Nullable Integer tintColor) {
        if (tintColor == null) {
            view.clearColorFilter();
        } else {
            view.setColorFilter(tintColor);
        }
    }

    @ReactProp(name = "scale")
    public void setScale(PhotoViewWrapper view, @Nullable float scale) {
        view.setCustomScale(scale);
    }

    @ReactProp(name = "scaleType")
    public void setScaleType(PhotoViewWrapper view, String scaleType) {
        ScaleType value = ScaleType.CENTER;

        switch (scaleType) {
            case "center":
                value = ScaleType.CENTER;
                break;
            case "centerCrop":
                value = ScaleType.CENTER_CROP;
                break;
            case "centerInside":
                value = ScaleType.CENTER_INSIDE;
                break;
            case "fitCenter":
                value = ScaleType.FIT_CENTER;
                break;
            case "fitStart":
                value = ScaleType.FIT_START;
                break;
            case "fitEnd":
                value = ScaleType.FIT_END;
                break;
            case "fitXY":
                value = ScaleType.FIT_XY;
                break;
            case "matrix":
                value = ScaleType.MATRIX;
                break;
        }

        view.setScaleType(value);
    }

}
