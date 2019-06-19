package com.ifhu.meiwei.utils;

import android.app.Activity;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.TextAppearanceSpan;
import android.text.style.UnderlineSpan;
import android.widget.TextView;

import com.ifhu.meiwei.MyApplication;

public class SpannableTextUtil {


    /**
     * 示例代码
     *
     * @param activity 要显示的上下文
     * @return TextView 显示的TextView
     */
    public static TextView getDisplay(Activity activity) {
        TextView tv = new TextView(activity);
        String simple = "相对大小";
        //可直接设置文本中
        tv.setText(addFontSpan(simple, 0.5f, new SpannableStringBuilder()));
        return tv;
    }

    /**
     * 设置一段文本中的起始标识到结束标识的文字的相对大小<br/>
     * 可以添加以下两个参数，增加扩展性
     * <p>{@code start 起始标识}<br/>
     * {@code end 结束标识}</p>
     *
     * @param s    需要设置的字符对象
     * @param size 要设置的相对大小
     * @param ssb  builder对象 可以用此对象添加前缀
     * @return 经过编辑后的builder类
     */
    public static SpannableStringBuilder addFontSpan(String s, float size, SpannableStringBuilder ssb) {
        SpannableString spanString = new SpannableString(s);
        RelativeSizeSpan span = new RelativeSizeSpan(size);
        //SPAN_EXCLUSIVE_EXCLUSIVE 指的是开始和结束标识的位置都包含在内
        //设置不包含标识符的可以用INCLUSIVE替换EXCLUSIVE即可
        spanString.setSpan(span, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spanString);
        return ssb;
    }

    /**
     * 设置文本中对应起始结束位置的文字颜色<br/>
     * 可以添加以下两个参数，增加扩展性
     * <p>{@code start 起始标识}</p>
     * <p>{@code end 结束标识}</p>
     *
     * @param s 需要进行编辑的字符对象
     * @param color 要设置的颜色
     * @param ssb builder对象
     */
    public static SpannableStringBuilder addForeColorSpan(CharSequence s, int color, SpannableStringBuilder ssb) {
        SpannableString spanString = new SpannableString(s);
        ForegroundColorSpan span = new ForegroundColorSpan(color);
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spanString);
        return ssb;
    }

    /**
     * 设置文本中对应起始结束位置的文字样式<br/>
     * 可以添加以下两个参数，增加扩展性
     * <p>{@code start 起始标识}</p>
     * <p>{@code end 结束标识}</p>
     *
     * @param s 要设置的字符对象
     * @param style 要设置的样式 详情参考{@link Typeface#BOLD_ITALIC}
     * @param ssb builder对象
     */
    public static SpannableStringBuilder addStyleSpan(CharSequence s, int style, SpannableStringBuilder ssb) {
        SpannableString spanString = new SpannableString("BIBI");
        StyleSpan span = new StyleSpan(Typeface.BOLD_ITALIC);
        spanString.setSpan(span, 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spanString);
        return ssb;
    }

    /**
     * 给文本中对应位置的文本添加下划线<br/>
     * 可以添加以下两个参数，增加扩展性
     * <p>{@code start 起始标识}</p>
     * <p>{@code end 结束标识}</p>
     *
     * @param s 要设置的文本
     * @param ssb builder对象
     * @return builder对象
     */
    public SpannableStringBuilder addUnderLineSpan(CharSequence s, SpannableStringBuilder ssb) {
        SpannableString spanString = new SpannableString(s);
        UnderlineSpan span = new UnderlineSpan();
        spanString.setSpan(span, 0, 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ssb.append(spanString);
        return ssb;
    }

    public static SpannableStringBuilder setTextContactStyle(String[] text,int[] styleId){
        SpannableStringBuilder contactSpannable = new SpannableStringBuilder();
        for (int i = 0; i < text.length; i++) {
            SpannableStringBuilder spannable=new SpannableStringBuilder(text[i]);
            TextAppearanceSpan sbbb=new TextAppearanceSpan(MyApplication.getApplication(), styleId[i]);
            spannable.setSpan(sbbb,0,text[i].length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            contactSpannable.append(spannable);
        }
        return contactSpannable;
    }

}
