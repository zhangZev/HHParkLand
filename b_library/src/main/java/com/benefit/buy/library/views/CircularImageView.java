package com.benefit.buy.library.views;

import com.benefit.buy.library.R;
import com.lidroid.xutils.bitmap.core.AsyncDrawable;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class CircularImageView extends ImageView {

    private int borderWidth;

    private int canvasSize;

    private Bitmap image;

    private final Paint paint;

    private final Paint paintBorder;

    public CircularImageView(final Context context) {
        this(context, null);
    }

    public CircularImageView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.circularImageViewStyle);
    }

    public CircularImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // init paint
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.paintBorder = new Paint();
        this.paintBorder.setAntiAlias(true);
        // load the styled attributes and set their properties
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView, defStyle, 0);
        if (attributes.getBoolean(R.styleable.CircularImageView_border, true)) {
            int defaultBorderSize = (int) ((2 * getContext().getResources().getDisplayMetrics().density) + 0.5f);
            setBorderWidth(
                    attributes.getDimensionPixelOffset(R.styleable.CircularImageView_border_width, defaultBorderSize));
            setBorderColor(attributes.getColor(R.styleable.CircularImageView_border_color, Color.WHITE));
        }
        if (attributes.getBoolean(R.styleable.CircularImageView_shadow, false)) {
            addShadow();
        }
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
        requestLayout();
        this.invalidate();
    }

    public void setBorderColor(int borderColor) {
        if (this.paintBorder != null) {
            this.paintBorder.setColor(borderColor);
        }
        this.invalidate();
    }

    @SuppressLint("NewApi")
    public void addShadow() {
        setLayerType(LAYER_TYPE_SOFTWARE, this.paintBorder);
        this.paintBorder.setShadowLayer(4.0f, 0.0f, 2.0f, Color.BLACK);
    }

    @Override
    public void onDraw(Canvas canvas) {
        // load the bitmap
        this.image = drawableToBitmap(getDrawable());
        // init shader
        if (this.image != null) {
            this.canvasSize = canvas.getWidth();
            if (canvas.getHeight() < this.canvasSize) {
                this.canvasSize = canvas.getHeight();
            }
            BitmapShader shader = new BitmapShader(
                    Bitmap.createScaledBitmap(this.image, this.canvasSize, this.canvasSize, false),
                    Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.paint.setShader(shader);
            // circleCenter is the x or y of the view's center
            // radius is the radius in pixels of the cirle to be drawn
            // paint contains the shader that will texture the shape
            int circleCenter = (this.canvasSize - (this.borderWidth * 2)) / 2;
            canvas.drawCircle(circleCenter + this.borderWidth, circleCenter + this.borderWidth,
                    (((this.canvasSize - (this.borderWidth * 2)) / 2) + this.borderWidth) - 4.0f, this.paintBorder);
            canvas.drawCircle(circleCenter + this.borderWidth, circleCenter + this.borderWidth,
                    ((this.canvasSize - (this.borderWidth * 2)) / 2) - 4.0f, this.paint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = measureWidth(widthMeasureSpec);
        int height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureWidth(int measureSpec) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY) {
            // The parent has determined an exact size for the child.
            result = specSize;
        }
        else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        }
        else {
            // The parent has not imposed any constraint on the child.
            result = this.canvasSize;
        }
        return result;
    }

    private int measureHeight(int measureSpecHeight) {
        int result = 0;
        int specMode = MeasureSpec.getMode(measureSpecHeight);
        int specSize = MeasureSpec.getSize(measureSpecHeight);
        if (specMode == MeasureSpec.EXACTLY) {
            // We were told how big to be
            result = specSize;
        }
        else if (specMode == MeasureSpec.AT_MOST) {
            // The child can be as large as it wants up to the specified size.
            result = specSize;
        }
        else {
            // Measure the text (beware: ascent is a negative number)
            result = this.canvasSize;
        }
        return (result + 2);
    }

    public Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable == null) {
            return null;
        }
        else if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        else if (drawable instanceof AsyncDrawable) {
            //            drawable = getDrawable();
            this.image = Bitmap.createBitmap(getWidth(), getHeight(),
                    drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
            Canvas canvas1 = new Canvas(this.image);
            // canvas.setBitmap(bitmap); 
            drawable.setBounds(0, 0, getWidth(), getHeight());
            drawable.draw(canvas1);
        }
        return this.image;
    }
}
