/**
 * LoadingDialog.java
 * classes : com.dld.view.LoadingDialog
 *
 * @author cdj
 * V 1.0.0 * Create at 2014-8-14 下午4:10:48
 */
package com.framework.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import com.framework.util.StringUtil;
import com.framework.R;
/**
 * @author caodejun
 * @version [V1.0]
 * @ClassName:[LoadingDialog]
 * @Description:[]
 * @CreateDate:[2014-8-14 下午4:10:48]
 * @UpdateUser: UpdateUser
 * @UpdateDate: [2014-8-14 下午4:10:48]
 * @UpdateRemark: [说明本次修改内容]
 */

public class LoadingDialog extends Dialog {
    private TextView tip_Tv;
    private ImageView loding_Iv;
    private Context ctx;

    public LoadingDialog(Context context) {
        this(context, R.style.dialog);
    }

    public LoadingDialog(Context context, int style) {
        super(context, style);
        this.ctx = context;
        setContentView(R.layout.dialog_loading);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        loding_Iv = (ImageView) findViewById(R.id.loding_Iv);
        tip_Tv = (TextView) findViewById(R.id.tip_Tv);
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        hyperspaceJumpAnimation.setInterpolator(new LinearInterpolator());
        loding_Iv.startAnimation(hyperspaceJumpAnimation);
        setCancelable(false);//
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * @param content
     * @Description:[设置加载信息]
     */
    public void setText(String content) {
        if (!StringUtil.isEmpty(content)) {
            tip_Tv.setText(content);
            tip_Tv.setVisibility(View.VISIBLE);
        }
    }
}
