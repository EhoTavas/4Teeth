// Generated by view binder compiler. Do not edit!
package br.com.ForTeethDentalCare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import br.com.ForTeethDentalCare.R;
import com.google.android.material.appbar.MaterialToolbar;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRequestedEmergencyBinding implements ViewBinding {
  @NonNull
  private final CoordinatorLayout rootView;

  @NonNull
  public final MaterialToolbar toolbar;

  private ActivityRequestedEmergencyBinding(@NonNull CoordinatorLayout rootView,
      @NonNull MaterialToolbar toolbar) {
    this.rootView = rootView;
    this.toolbar = toolbar;
  }

  @Override
  @NonNull
  public CoordinatorLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRequestedEmergencyBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRequestedEmergencyBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_requested_emergency, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRequestedEmergencyBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.toolbar;
      MaterialToolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      return new ActivityRequestedEmergencyBinding((CoordinatorLayout) rootView, toolbar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
