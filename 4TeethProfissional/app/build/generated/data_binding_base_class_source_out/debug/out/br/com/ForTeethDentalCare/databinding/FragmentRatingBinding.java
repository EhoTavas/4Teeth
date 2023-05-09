// Generated by view binder compiler. Do not edit!
package br.com.ForTeethDentalCare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import br.com.ForTeethDentalCare.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentRatingBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final AppCompatButton btnAvaliacaoNao;

  @NonNull
  public final AppCompatButton btnAvaliacaoSim;

  @NonNull
  public final LinearLayoutCompat elevation;

  @NonNull
  public final ImageView imageView;

  private FragmentRatingBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull AppCompatButton btnAvaliacaoNao, @NonNull AppCompatButton btnAvaliacaoSim,
      @NonNull LinearLayoutCompat elevation, @NonNull ImageView imageView) {
    this.rootView = rootView;
    this.btnAvaliacaoNao = btnAvaliacaoNao;
    this.btnAvaliacaoSim = btnAvaliacaoSim;
    this.elevation = elevation;
    this.imageView = imageView;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentRatingBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentRatingBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_rating, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentRatingBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btnAvaliacaoNao;
      AppCompatButton btnAvaliacaoNao = ViewBindings.findChildViewById(rootView, id);
      if (btnAvaliacaoNao == null) {
        break missingId;
      }

      id = R.id.btnAvaliacaoSim;
      AppCompatButton btnAvaliacaoSim = ViewBindings.findChildViewById(rootView, id);
      if (btnAvaliacaoSim == null) {
        break missingId;
      }

      LinearLayoutCompat elevation = (LinearLayoutCompat) rootView;

      id = R.id.imageView;
      ImageView imageView = ViewBindings.findChildViewById(rootView, id);
      if (imageView == null) {
        break missingId;
      }

      return new FragmentRatingBinding((LinearLayoutCompat) rootView, btnAvaliacaoNao,
          btnAvaliacaoSim, elevation, imageView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
