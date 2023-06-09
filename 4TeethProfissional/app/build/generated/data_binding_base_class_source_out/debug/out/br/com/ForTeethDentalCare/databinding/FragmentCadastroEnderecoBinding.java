// Generated by view binder compiler. Do not edit!
package br.com.ForTeethDentalCare.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import br.com.ForTeethDentalCare.R;
import com.santalu.maskara.widget.MaskEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentCadastroEnderecoBinding implements ViewBinding {
  @NonNull
  private final LinearLayoutCompat rootView;

  @NonNull
  public final MaskEditText EtCep1;

  @NonNull
  public final MaskEditText EtCep2;

  @NonNull
  public final MaskEditText EtCep3;

  @NonNull
  public final AppCompatEditText EtCity1;

  @NonNull
  public final AppCompatEditText EtCity2;

  @NonNull
  public final AppCompatEditText EtCity3;

  @NonNull
  public final AppCompatEditText EtComp1;

  @NonNull
  public final AppCompatEditText EtComp2;

  @NonNull
  public final AppCompatEditText EtComp3;

  @NonNull
  public final AppCompatEditText EtNh1;

  @NonNull
  public final AppCompatEditText EtNh2;

  @NonNull
  public final AppCompatEditText EtNh3;

  @NonNull
  public final AppCompatEditText EtNum1;

  @NonNull
  public final AppCompatEditText EtNum2;

  @NonNull
  public final AppCompatEditText EtNum3;

  @NonNull
  public final AppCompatEditText EtState1;

  @NonNull
  public final AppCompatEditText EtState2;

  @NonNull
  public final MaskEditText EtState3;

  @NonNull
  public final AppCompatEditText EtStreet1;

  @NonNull
  public final AppCompatEditText EtStreet2;

  @NonNull
  public final AppCompatEditText EtStreet3;

  @NonNull
  public final LinearLayoutCompat LayoutAddresses;

  @NonNull
  public final AppCompatTextView TvEndereco1;

  @NonNull
  public final AppCompatTextView TvEndereco2;

  @NonNull
  public final AppCompatTextView TvEndereco3;

  @NonNull
  public final AppCompatTextView TvError;

  @NonNull
  public final AppCompatButton btnConfirmar;

  private FragmentCadastroEnderecoBinding(@NonNull LinearLayoutCompat rootView,
      @NonNull MaskEditText EtCep1, @NonNull MaskEditText EtCep2, @NonNull MaskEditText EtCep3,
      @NonNull AppCompatEditText EtCity1, @NonNull AppCompatEditText EtCity2,
      @NonNull AppCompatEditText EtCity3, @NonNull AppCompatEditText EtComp1,
      @NonNull AppCompatEditText EtComp2, @NonNull AppCompatEditText EtComp3,
      @NonNull AppCompatEditText EtNh1, @NonNull AppCompatEditText EtNh2,
      @NonNull AppCompatEditText EtNh3, @NonNull AppCompatEditText EtNum1,
      @NonNull AppCompatEditText EtNum2, @NonNull AppCompatEditText EtNum3,
      @NonNull AppCompatEditText EtState1, @NonNull AppCompatEditText EtState2,
      @NonNull MaskEditText EtState3, @NonNull AppCompatEditText EtStreet1,
      @NonNull AppCompatEditText EtStreet2, @NonNull AppCompatEditText EtStreet3,
      @NonNull LinearLayoutCompat LayoutAddresses, @NonNull AppCompatTextView TvEndereco1,
      @NonNull AppCompatTextView TvEndereco2, @NonNull AppCompatTextView TvEndereco3,
      @NonNull AppCompatTextView TvError, @NonNull AppCompatButton btnConfirmar) {
    this.rootView = rootView;
    this.EtCep1 = EtCep1;
    this.EtCep2 = EtCep2;
    this.EtCep3 = EtCep3;
    this.EtCity1 = EtCity1;
    this.EtCity2 = EtCity2;
    this.EtCity3 = EtCity3;
    this.EtComp1 = EtComp1;
    this.EtComp2 = EtComp2;
    this.EtComp3 = EtComp3;
    this.EtNh1 = EtNh1;
    this.EtNh2 = EtNh2;
    this.EtNh3 = EtNh3;
    this.EtNum1 = EtNum1;
    this.EtNum2 = EtNum2;
    this.EtNum3 = EtNum3;
    this.EtState1 = EtState1;
    this.EtState2 = EtState2;
    this.EtState3 = EtState3;
    this.EtStreet1 = EtStreet1;
    this.EtStreet2 = EtStreet2;
    this.EtStreet3 = EtStreet3;
    this.LayoutAddresses = LayoutAddresses;
    this.TvEndereco1 = TvEndereco1;
    this.TvEndereco2 = TvEndereco2;
    this.TvEndereco3 = TvEndereco3;
    this.TvError = TvError;
    this.btnConfirmar = btnConfirmar;
  }

  @Override
  @NonNull
  public LinearLayoutCompat getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentCadastroEnderecoBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentCadastroEnderecoBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_cadastro_endereco, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentCadastroEnderecoBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.EtCep1;
      MaskEditText EtCep1 = ViewBindings.findChildViewById(rootView, id);
      if (EtCep1 == null) {
        break missingId;
      }

      id = R.id.EtCep2;
      MaskEditText EtCep2 = ViewBindings.findChildViewById(rootView, id);
      if (EtCep2 == null) {
        break missingId;
      }

      id = R.id.EtCep3;
      MaskEditText EtCep3 = ViewBindings.findChildViewById(rootView, id);
      if (EtCep3 == null) {
        break missingId;
      }

      id = R.id.EtCity1;
      AppCompatEditText EtCity1 = ViewBindings.findChildViewById(rootView, id);
      if (EtCity1 == null) {
        break missingId;
      }

      id = R.id.EtCity2;
      AppCompatEditText EtCity2 = ViewBindings.findChildViewById(rootView, id);
      if (EtCity2 == null) {
        break missingId;
      }

      id = R.id.EtCity3;
      AppCompatEditText EtCity3 = ViewBindings.findChildViewById(rootView, id);
      if (EtCity3 == null) {
        break missingId;
      }

      id = R.id.EtComp1;
      AppCompatEditText EtComp1 = ViewBindings.findChildViewById(rootView, id);
      if (EtComp1 == null) {
        break missingId;
      }

      id = R.id.EtComp2;
      AppCompatEditText EtComp2 = ViewBindings.findChildViewById(rootView, id);
      if (EtComp2 == null) {
        break missingId;
      }

      id = R.id.EtComp3;
      AppCompatEditText EtComp3 = ViewBindings.findChildViewById(rootView, id);
      if (EtComp3 == null) {
        break missingId;
      }

      id = R.id.EtNh1;
      AppCompatEditText EtNh1 = ViewBindings.findChildViewById(rootView, id);
      if (EtNh1 == null) {
        break missingId;
      }

      id = R.id.EtNh2;
      AppCompatEditText EtNh2 = ViewBindings.findChildViewById(rootView, id);
      if (EtNh2 == null) {
        break missingId;
      }

      id = R.id.EtNh3;
      AppCompatEditText EtNh3 = ViewBindings.findChildViewById(rootView, id);
      if (EtNh3 == null) {
        break missingId;
      }

      id = R.id.EtNum1;
      AppCompatEditText EtNum1 = ViewBindings.findChildViewById(rootView, id);
      if (EtNum1 == null) {
        break missingId;
      }

      id = R.id.EtNum2;
      AppCompatEditText EtNum2 = ViewBindings.findChildViewById(rootView, id);
      if (EtNum2 == null) {
        break missingId;
      }

      id = R.id.EtNum3;
      AppCompatEditText EtNum3 = ViewBindings.findChildViewById(rootView, id);
      if (EtNum3 == null) {
        break missingId;
      }

      id = R.id.EtState1;
      AppCompatEditText EtState1 = ViewBindings.findChildViewById(rootView, id);
      if (EtState1 == null) {
        break missingId;
      }

      id = R.id.EtState2;
      AppCompatEditText EtState2 = ViewBindings.findChildViewById(rootView, id);
      if (EtState2 == null) {
        break missingId;
      }

      id = R.id.EtState3;
      MaskEditText EtState3 = ViewBindings.findChildViewById(rootView, id);
      if (EtState3 == null) {
        break missingId;
      }

      id = R.id.EtStreet1;
      AppCompatEditText EtStreet1 = ViewBindings.findChildViewById(rootView, id);
      if (EtStreet1 == null) {
        break missingId;
      }

      id = R.id.EtStreet2;
      AppCompatEditText EtStreet2 = ViewBindings.findChildViewById(rootView, id);
      if (EtStreet2 == null) {
        break missingId;
      }

      id = R.id.EtStreet3;
      AppCompatEditText EtStreet3 = ViewBindings.findChildViewById(rootView, id);
      if (EtStreet3 == null) {
        break missingId;
      }

      id = R.id.LayoutAddresses;
      LinearLayoutCompat LayoutAddresses = ViewBindings.findChildViewById(rootView, id);
      if (LayoutAddresses == null) {
        break missingId;
      }

      id = R.id.TvEndereco1;
      AppCompatTextView TvEndereco1 = ViewBindings.findChildViewById(rootView, id);
      if (TvEndereco1 == null) {
        break missingId;
      }

      id = R.id.TvEndereco2;
      AppCompatTextView TvEndereco2 = ViewBindings.findChildViewById(rootView, id);
      if (TvEndereco2 == null) {
        break missingId;
      }

      id = R.id.TvEndereco3;
      AppCompatTextView TvEndereco3 = ViewBindings.findChildViewById(rootView, id);
      if (TvEndereco3 == null) {
        break missingId;
      }

      id = R.id.TvError;
      AppCompatTextView TvError = ViewBindings.findChildViewById(rootView, id);
      if (TvError == null) {
        break missingId;
      }

      id = R.id.btnConfirmar;
      AppCompatButton btnConfirmar = ViewBindings.findChildViewById(rootView, id);
      if (btnConfirmar == null) {
        break missingId;
      }

      return new FragmentCadastroEnderecoBinding((LinearLayoutCompat) rootView, EtCep1, EtCep2,
          EtCep3, EtCity1, EtCity2, EtCity3, EtComp1, EtComp2, EtComp3, EtNh1, EtNh2, EtNh3, EtNum1,
          EtNum2, EtNum3, EtState1, EtState2, EtState3, EtStreet1, EtStreet2, EtStreet3,
          LayoutAddresses, TvEndereco1, TvEndereco2, TvEndereco3, TvError, btnConfirmar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
