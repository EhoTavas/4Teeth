// Generated by view binder compiler. Do not edit!
package br.com.ForTeethDentalCare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import br.com.ForTeethDentalCare.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentUserBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final AppCompatButton btnLogout;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final AppCompatTextView tvUserMail;

  @NonNull
  public final AppCompatTextView tvUserName;

  @NonNull
  public final AppCompatTextView tvUserPhone;

  private FragmentUserBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull AppCompatButton btnLogout, @NonNull ImageView imageView,
      @NonNull AppCompatTextView tvUserMail, @NonNull AppCompatTextView tvUserName,
      @NonNull AppCompatTextView tvUserPhone) {
    this.rootView = rootView;
    this.btnLogout = btnLogout;
    this.imageView = imageView;
    this.tvUserMail = tvUserMail;
    this.tvUserName = tvUserName;
    this.tvUserPhone = tvUserPhone;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentUserBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentUserBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_user, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentUserBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnLogout;
      AppCompatButton btnLogout = ViewBindings.findChildViewById(rootView, id);
      if (btnLogout == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.tvUserMail;
      AppCompatTextView tvUserMail = ViewBindings.findChildViewById(rootView, id);
      if (tvUserMail == null) {
        break missingId;
      }

      id = R.id.tvUserName;
      AppCompatTextView tvUserName = ViewBindings.findChildViewById(rootView, id);
      if (tvUserName == null) {
        break missingId;
      }

      id = R.id.tvUserPhone;
      AppCompatTextView tvUserPhone = ViewBindings.findChildViewById(rootView, id);
      if (tvUserPhone == null) {
        break missingId;
      }

      return new FragmentUserBinding((LinearLayoutCompat) rootView, btnLogout, imageView,
          tvUserMail, tvUserName, tvUserPhone);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
