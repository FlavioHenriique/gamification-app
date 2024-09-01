package io.github.gamification.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.WindowManager;

import io.github.gamification.dialog.DialogInsigniaFragment;

public class ShowDialog {

    private String TAG = this.getClass().getSimpleName();

    public static ProgressDialog showDialogIndeterminado(Activity context, String mensagem) {
        ProgressDialog dialogIndeterminado = new ProgressDialog(context);
        dialogIndeterminado.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_PANEL);
        dialogIndeterminado.setProgress(0);
        dialogIndeterminado.setIndeterminate(true);
        dialogIndeterminado.setCancelable(false);
        if (!TextUtils.isEmpty(mensagem))
            dialogIndeterminado.setMessage(mensagem);
        dialogIndeterminado.setProgressStyle(dialogIndeterminado.STYLE_SPINNER);
        return dialogIndeterminado;
    }

    public static void showDialogInsignia(Activity activity, String name, String titulo, String descricao) {
        Bundle bundle = new Bundle();
        bundle.putString("TITULO", titulo);
        bundle.putString("IMG_NAME", name);
        bundle.putString("DESCRICAO", descricao);
        DialogInsigniaFragment fragment = new DialogInsigniaFragment();
        fragment.setArguments(bundle);
        fragment.show(activity.getFragmentManager(), "INSIGNIA");
    }
}
