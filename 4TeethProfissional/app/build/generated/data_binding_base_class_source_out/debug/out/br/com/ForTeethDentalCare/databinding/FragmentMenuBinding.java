// Generated by view binder compiler. Do not edit!
package br.com.ForTeethDentalCare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import br.com.ForTeethDentalCare.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMenuBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnLogin;

  @NonNull
  public final Button btnLogin2;

  @NonNull
  public final Button btnLogin3;

  private FragmentMenuBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnLogin,
      @NonNull Button btnLogin2, @NonNull Button btnLogin3) {
    this.rootView = rootView;
    this.btnLogin = btnLogin;
    this.btnLogin2 = btnLogin2;
    this.btnLogin3 = btnLogin3;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
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
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnLogin;
      Button btnLogin = ViewBindings.findChildViewById(rootView, id);
      if (btnLogin == null) {
        break missingId;
      }

      id = R.id.btnLogin2;
      Button btnLogin2 = ViewBindings.findChildViewById(rootView, id);
      if (btnLogin2 == null) {
        break missingId;
      }

      id = R.id.btnLogin3;
      Button btnLogin3 = ViewBindings.findChildViewById(rootView, id);
      if (btnLogin3 == null) {
        break missingId;
      }

      return new FragmentMenuBinding((ConstraintLayout) rootView, btnLogin, btnLogin2, btnLogin3);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
