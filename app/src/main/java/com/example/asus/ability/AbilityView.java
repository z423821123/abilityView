package com.example.asus.ability;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZWX
 * Description:
 * on 2019/1/3.
 */
public class AbilityView extends View {

    /**
     * 外圈线条颜色,默认为灰色
     */
    private final int DEFAULT_LINE_LENGTH = 200;

    /**
     * 外圈线条颜色,默认为灰色
     */
    private final int DEFAULT_OUT_LINE_COLOR = 0xFFD0D6DC;

    /**
     * 内圈线条颜色,默认为灰色
     */
    private final int DEFAULT_IN_LINE_COLOR = 0xFFD0D6DC;

    /**
     * 外圈线条宽度默认为3
     */
    private final int DEFAULT_OUT_LINE_WIDTH = 3;

    /**
     * 内圈线条宽度默认为1
     */
    private final int DEFAULT_IN_LINE_WIDTH = 1;

    /**
     * 能力图线条宽度默认为4
     */
    private final int DEFAULT_ABILITY_LINE_WIDTH = 4;

    /**
     * 能力图线条颜色,默认为蓝色
     */
    private final int DEFAULT_ABILITY_LINE_COLOR = 0x8897C5FE;

    /**
     * 能力图数值大小
     */
    private final int DEFAULT_ABILITY_NUMBER_SIZE = 25;

    /**
     * 能力图文字大小
     */
    private final int DEFAULT_ABILITY_TEXT_SIZE = 40;

    /**
     * 文本数字颜色和文本数字大小, 默认为黑色，10px
     */
    private final int DEFAULT_TEXT_COLOR = 0xff647d91;
    private final int DEFAULT_TEXT_SIZE = 30;
    private final int DEFAULT_Number_COLOR = 0xff647d91;
    private final int DEFAULT_Number_SIZE = 14;

    //显示类型
    private int TYPE = TYPE_HORIZONTAL;
    final static int TYPE_VERTICAL = 1;
    final static int TYPE_HORIZONTAL = 2;


    private Paint mPolygonOutPaint;
    private Paint mPolygonInPaint;
    private TextPaint mTextPaint;
    private Path mPath;
    private Path mAbilityPath;
    private Paint mAbilityPaint;

    private int mAngleNumber ;
    private int mNumberSize;
    private int mNumberColor;
    private int mTextSize;
    private int mTextColor;
    private int mInLineWidth;
    private int mInLineColor;
    private int mOutLineWidth;
    private int mOutLineColor;
    private int mLineLength;
    private int mAbilityLineWidth;
    private int mAbilityLineColor;

    private int realWidth, realHeiht;

    private List<AbilityBean> abilityBeans = new ArrayList<>();

    public AbilityView(Context context) {
        super(context);
    }

    public AbilityView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public AbilityView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray attributes = getContext().obtainStyledAttributes(attrs, R.styleable.AbilityView);

        mNumberColor = attributes.getColor(R.styleable.AbilityView_radarNumberColor, DEFAULT_Number_COLOR);
        mNumberSize = attributes.getDimensionPixelSize(R.styleable.AbilityView_radarNumberSize, DEFAULT_Number_SIZE);
        mTextColor = attributes.getColor(R.styleable.AbilityView_radarTextColor, DEFAULT_TEXT_COLOR);
        mTextSize = attributes.getDimensionPixelSize(R.styleable.AbilityView_radarTextSize, DEFAULT_TEXT_SIZE);
        mInLineColor = attributes.getColor(R.styleable.AbilityView_radarInLineColor, DEFAULT_IN_LINE_COLOR);
        mInLineWidth = attributes.getDimensionPixelSize(R.styleable.AbilityView_radarInLineWidth, DEFAULT_IN_LINE_WIDTH);
        mOutLineColor = attributes.getColor(R.styleable.AbilityView_radarOutLineColor, DEFAULT_OUT_LINE_COLOR);
        mOutLineWidth = attributes.getDimensionPixelSize(R.styleable.AbilityView_radarOutLineWidth, DEFAULT_OUT_LINE_WIDTH);
        mLineLength = attributes.getDimensionPixelSize(R.styleable.AbilityView_radarLineLength, DEFAULT_LINE_LENGTH);
        mAbilityLineWidth = attributes.getDimensionPixelSize(R.styleable.AbilityView_radarAbilityLineWidth, DEFAULT_ABILITY_LINE_WIDTH);
        mAbilityLineColor = attributes.getColor(R.styleable.AbilityView_radarAbilityLineColor, DEFAULT_ABILITY_LINE_COLOR);

        mAngleNumber = abilityBeans.size();
        initPaint();
        attributes.recycle();
    }

    private void initPaint() {
        mPolygonOutPaint = new Paint();
        mPolygonOutPaint.setColor(mOutLineColor);
        mPolygonOutPaint.setAntiAlias(true);
        mPolygonOutPaint.setStrokeWidth(mOutLineWidth);
        mPolygonOutPaint.setStyle(Paint.Style.STROKE);

        mPolygonInPaint = new Paint();
        mPolygonInPaint.setColor(mInLineColor);
        mPolygonInPaint.setAntiAlias(true);
        mPolygonInPaint.setStrokeWidth(mInLineWidth);
        mPolygonInPaint.setStyle(Paint.Style.STROKE);

        mTextPaint = new TextPaint();
        mTextPaint.setColor(mInLineColor);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStrokeWidth(mInLineWidth);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mAbilityPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mAbilityPaint.setColor(DEFAULT_ABILITY_LINE_COLOR);
        mAbilityPaint.setAntiAlias(true);
        mAbilityPaint.setStrokeWidth(DEFAULT_ABILITY_LINE_WIDTH);

        mPath = new Path();
        mAbilityPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (abilityBeans == null || abilityBeans.size() == 0) {
            throw new NullPointerException("请先设置数据集");
        }
        canvas.translate(mLineLength + 80,  mLineLength + 80);

        //画多边形
        drawPolygon(canvas, mLineLength, mPolygonOutPaint);
        drawPolygon(canvas, 0.25f * mLineLength , mPolygonInPaint);
        drawPolygon(canvas, 0.5f * mLineLength, mPolygonInPaint);
        drawPolygon(canvas, 0.75f * mLineLength, mPolygonInPaint);
        //连接中心点到各个边角
        drawLine(canvas);
        //画文字
        drawText(canvas);
        //画内圈能力图
        drawAbility(canvas);

        if(TYPE == TYPE_HORIZONTAL) {
            setPivotX(mLineLength + 80);
            setPivotY(mLineLength + 80);
            setRotation(30);
        }
    }

    private void drawAbility(Canvas canvas) {
        Log.e("============>","onDraw");
        canvas.save();
        float length = (float)(Double.parseDouble(abilityBeans.get(0).getAbilityCore())/ 100) * mLineLength;
        float angle = 360f / mAngleNumber;
        mAbilityPath.moveTo(0,length);
        for(int i = 0; i < mAngleNumber; i++) {
            canvas.save();
            double mPolygonLength = ((Double.parseDouble((abilityBeans.get(i).abilityCore)) / 100) * mLineLength);//多边形边长
            //triangle
            double x = mPolygonLength * Math.cos(Math.toRadians(90 + angle * i));
            double y =  mPolygonLength * Math.sin(Math.toRadians(90 + angle * i));

            mAbilityPath.lineTo((float) x, (float) y);
            canvas.restore();
        }

        mAbilityPath.close();
        canvas.drawPath(mAbilityPath, mAbilityPaint);
        canvas.restore();
    }

    private void drawLine(Canvas canvas) {
        canvas.save();
        float angle = 360f / mAngleNumber;
        for(int i = 0; i < mAngleNumber; i++) {
            canvas.save();
            canvas.rotate(i * angle);
            mPath.moveTo(0, 0);
            mPath.lineTo(0,mLineLength);
            canvas.drawPath(mPath, mPolygonInPaint);
            canvas.restore();
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();

        float angle = 360f / mAngleNumber;
        for(int i = 0; i < mAngleNumber; i++) {
            canvas.save();
            canvas.rotate(i * angle);

            mTextPaint.setTextSize(px2dp(getContext(),(mTextSize == 0) ? DEFAULT_Number_SIZE : mTextSize));
            canvas.drawText(abilityBeans.get(i).getAbilityName(),0, (float)(mLineLength + (mLineLength * 0.1)), mTextPaint);
            mTextPaint.setTextSize(px2dp(getContext(), (mNumberSize == 0) ? DEFAULT_Number_SIZE : mNumberSize));
            canvas.drawText(abilityBeans.get(i).getAbilityCore(), 0, (float)(mLineLength + (mLineLength * 0.3)), mTextPaint);
            canvas.restore();
        }

        canvas.restore();
    }

    private void drawPolygon(Canvas canvas, float length, Paint paint) {
        canvas.save();
        float angle = 360f / mAngleNumber;
        double mPolygonLength = 2 * length * Math.sin(Math.toRadians(angle / 2f)); //多边形边长

        //triangle
        double x = mPolygonLength * Math.sin(Math.toRadians(angle));
        float yAngle = 180 - angle - 90;
        double y =  mPolygonLength * Math.sin(Math.toRadians(yAngle));
        for(int i = 0; i < mAngleNumber; i++) {
            canvas.save();
            canvas.rotate(i * angle);
            mPath.moveTo(0,length);
            mPath.lineTo((float) x, (float) (length - y));
            canvas.drawPath(mPath, paint);
            canvas.restore();
        }
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measure(widthMeasureSpec);
        measure(heightMeasureSpec);
        Log.e("onMeasure", "realWidth: " + realWidth + "realHeiht: " + realHeiht + "widthMeasureSpec" + widthMeasureSpec + "heightMeasureSpec" + heightMeasureSpec);
        setMeasuredDimension(realWidth, realHeiht);
    }

    private void measure(int measureValue) {
        int defalueWidthSize = 150;//默认宽度
        int defalueHeightSize = 200;//默认高度
        int mode = MeasureSpec.getMode(measureValue);
        int specValue = MeasureSpec.getSize(measureValue);
        Log.e("onMeasure", "mode: " + mode + "specValue: " + specValue);
        switch (mode) {
            //指定一个默认值
            case MeasureSpec.UNSPECIFIED:
                Log.e("onMeasure", "mode: " + mode + "UNSPECIFIED ");
                realWidth = defalueWidthSize;
                realHeiht = defalueHeightSize;
                break;
            //取测量值
            case MeasureSpec.EXACTLY:
                Log.e("onMeasure", "mode: " + mode + "EXACTLY ");
                realHeiht = specValue;
                realWidth = specValue;
                break;
            //取测量值和默认值中的最小值
            case MeasureSpec.AT_MOST:
                Log.e("onMeasure", "mode: " + mode + "AT_MOST ");
                realWidth = Math.min(defalueWidthSize, specValue);
                realHeiht = Math.min(defalueHeightSize, specValue);
                break;
            default:
                break;
        }
    }



    public int getNumberSize() {
        return mNumberSize;
    }

    public void setNumberSize(int mNumberSize) {
        this.mNumberSize = mNumberSize;
    }

    public int getNumberColor() {
        return mNumberColor;
    }

    public void setNumberColor(int mNumberColor) {
        this.mNumberColor = mNumberColor;
    }

    public int getTextSize() {
        return mTextSize;
    }

    public void setTextSize(int mTextSize) {
        this.mTextSize = mTextSize;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public void setTextColor(int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getInLineWidth() {
        return mInLineWidth;
    }

    public void setInLineWidth(int mInLineWidth) {
        this.mInLineWidth = mInLineWidth;
    }

    public int getInLineColor() {
        return mInLineColor;
    }

    public void setInLineColor(int mInLineColor) {
        this.mInLineColor = mInLineColor;
    }

    public int getOutLineWidth() {
        return mOutLineWidth;
    }

    public void setOutLineWidth(int mOutLineWidth) {
        this.mOutLineWidth = mOutLineWidth;
    }

    public int getOutLineColor() {
        return mOutLineColor;
    }

    public void setOutLineColor(int mOutLineColor) {
        this.mOutLineColor = mOutLineColor;
    }

    public List<AbilityBean> getAbilityBeans() {
        return abilityBeans;
    }

    public void setAbilityBeans(List<AbilityBean> abilityBeans) {
        this.abilityBeans = abilityBeans;
        mAngleNumber = abilityBeans.size();
    }

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    /**
     * px转换成dp
     */
    private int px2dp(Context context,float pxValue){
        float scale=context.getResources().getDisplayMetrics().density;
        return (int)(pxValue/scale+0.5f);
    }

}
