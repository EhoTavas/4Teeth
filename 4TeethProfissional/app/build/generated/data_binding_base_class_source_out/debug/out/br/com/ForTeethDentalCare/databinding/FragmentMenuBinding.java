// Generated by view binder compiler. Do not edit!
package br.com.ForTeethDentalCare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import br.com.ForTeethDentalCare.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class FragmentMenuBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  private FragmentMenuBinding(@NonNull LinearLayoutCompat rootView) {
    this.rootView = rootView;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMenuBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMenuBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_menu, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMenuBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    return new FragmentMenuBinding((LinearLayoutCompat) rootView);
  }
}
