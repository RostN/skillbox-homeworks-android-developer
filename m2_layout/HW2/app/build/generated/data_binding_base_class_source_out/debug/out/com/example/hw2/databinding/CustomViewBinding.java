// Generated by view binder compiler. Do not edit!
package com.example.hw2.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.hw2.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CustomViewBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final TextView blackBox;

  @NonNull
  public final TextView blueBox;

  @NonNull
  public final TextView firstTetLine;

  @NonNull
  public final TextView secondTextLine;

  private CustomViewBinding(@NonNull RelativeLayout rootView, @NonNull TextView blackBox,
      @NonNull TextView blueBox, @NonNull TextView firstTetLine, @NonNull TextView secondTextLine) {
    this.rootView = rootView;
    this.blackBox = blackBox;
    this.blueBox = blueBox;
    this.firstTetLine = firstTetLine;
    this.secondTextLine = secondTextLine;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CustomViewBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CustomViewBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.custom_view, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CustomViewBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.blackBox;
      TextView blackBox = ViewBindings.findChildViewById(rootView, id);
      if (blackBox == null) {
        break missingId;
      }

      id = R.id.blueBox;
      TextView blueBox = ViewBindings.findChildViewById(rootView, id);
      if (blueBox == null) {
        break missingId;
      }

      id = R.id.firstTetLine;
      TextView firstTetLine = ViewBindings.findChildViewById(rootView, id);
      if (firstTetLine == null) {
        break missingId;
      }

      id = R.id.secondTextLine;
      TextView secondTextLine = ViewBindings.findChildViewById(rootView, id);
      if (secondTextLine == null) {
        break missingId;
      }

      return new CustomViewBinding((RelativeLayout) rootView, blackBox, blueBox, firstTetLine,
          secondTextLine);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
