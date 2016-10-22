/**
 *  The app displays the colors according to the seekbar's hue,saturation and value combination. It also displays colors based on
 *  the color selected from the button and sets the hue,saturation and value seekbars.
 *  @author Vaibhavi Desai (desa0068@algonquinlive.com)
 */

package com.algonquincollege.desa0068.hsvcolorpicker;

import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.algonquincollege.desa0068.hsvcolorpicker.model.HSVModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements Observer, SeekBar.OnSeekBarChangeListener, View.OnClickListener, View.OnLongClickListener {
    private static final int[] BUTTON_IDS = {
            R.id.blackButton,
            R.id.blueButton,
            R.id.cyanButton,
            R.id.grayButton,
            R.id.greenButton,
            R.id.limeButton,
            R.id.magentaButton,
            R.id.maroonButton,
            R.id.navyButton,
            R.id.oliveButton,
            R.id.purpleButton,
            R.id.redButton,
            R.id.silverButton,
            R.id.tealButton,
            R.id.yellowButton
    };
    private static final String ABOUT_DIALOG_TAG = "ABOUT";
    HSVModel hsvmodel;
    TextView colorSwatch, huetitle, saturationtitle, valuetitle;
    float hsv[];
    SeekBar hueseekbar, saturationseekbar, valueseekbar;
    Button button;
    ColorDrawable buttonBackground;
    private List<Button> buttons = new ArrayList<>();
    private AboutDialogFragment mAboutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        hsv = new float[3];
        mAboutDialog = new AboutDialogFragment();
        hsvmodel = new HSVModel();
        hsvmodel.setHue(HSVModel.MIN_VALUE);
        hsvmodel.setSaturation(HSVModel.MIN_VALUE);
        hsvmodel.setValue(HSVModel.MIN_VALUE);
        hsvmodel.addObserver(this);

        hueseekbar = (SeekBar) findViewById(R.id.huesb);
        saturationseekbar = (SeekBar) findViewById(R.id.satsb);
        valueseekbar = (SeekBar) findViewById(R.id.valuesb);
        colorSwatch = (TextView) findViewById(R.id.colorSwatch);

        hueseekbar.setMax((int) hsvmodel.MAX_HUE);
        saturationseekbar.setMax((int) hsvmodel.MAX_SATURATION);
        valueseekbar.setMax((int) hsvmodel.MAX_VALUE);

        huetitle = (TextView) findViewById(R.id.htitle);
        saturationtitle = (TextView) findViewById(R.id.stitle);
        valuetitle = (TextView) findViewById(R.id.value);

        for (int id : BUTTON_IDS) {
            button = (Button) findViewById(id);
            button.setOnClickListener(this); // maybe
            buttons.add(button);
        }

        hueseekbar.setOnSeekBarChangeListener(this);
        saturationseekbar.setOnSeekBarChangeListener(this);
        valueseekbar.setOnSeekBarChangeListener(this);
        colorSwatch.setOnLongClickListener(this);
        updateView();

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser == false) {
            return;
        }
        switch (seekBar.getId()) {

            case R.id.huesb:
                hsv[0] = (float) hueseekbar.getProgress();
                huetitle.setTypeface(null, Typeface.BOLD);
                huetitle.setText("HUE:" + hueseekbar.getProgress() + "\u00B0");
                hsvmodel.setHue((float) hueseekbar.getProgress());
                break;

            case R.id.satsb:
                hsv[1] = (float) saturationseekbar.getProgress();
                saturationtitle.setTypeface(null, Typeface.BOLD);
                saturationtitle.setText("SATURATION:" + saturationseekbar.getProgress() + "%");
                hsvmodel.setSaturation((float) saturationseekbar.getProgress());
                break;


            case R.id.valuesb:
                hsv[2] = (float) valueseekbar.getProgress();
                valuetitle.setTypeface(null, Typeface.BOLD);
                valuetitle.setText("VALUE:" + valueseekbar.getProgress() + "%");
                hsvmodel.setValue((float) valueseekbar.getProgress());
                break;
        }
    }


    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        huetitle.setTypeface(null, Typeface.NORMAL);
        huetitle.setText(getResources().getString(R.string.huetitle));
        saturationtitle.setTypeface(null, Typeface.NORMAL);
        saturationtitle.setText(getResources().getString(R.string.saturationtitle));
        valuetitle.setTypeface(null, Typeface.NORMAL);
        valuetitle.setText(getResources().getString(R.string.valtitle));
    }


    @Override
    public void update(Observable o, Object arg) {
        updateView();
    }

    private void updateHue() {
        hueseekbar.setProgress((int) hsvmodel.getHue());
    }

    private void updateSaturation() {
        saturationseekbar.setProgress((int) hsvmodel.getSaturation());
    }

    private void updateValue() {
        valueseekbar.setProgress((int) hsvmodel.getValue());
    }


    private void updateView() {
        updateColorSwatch();
        updateHue();
        updateSaturation();
        updateValue();
    }

    private void updateColorSwatch() {
        colorSwatch.setBackgroundColor(hsvmodel.getHSVColor());
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        buttonBackground = (ColorDrawable) (findViewById(id)).getBackground();
        hsv = hsvmodel.getColorToHSV(buttonBackground.getColor());
        hsvmodel.setHue(hsv[0]);
        hsvmodel.setSaturation(hsv[1]);
        hsvmodel.setValue(hsv[2]);
        Toast.makeText(this.getApplication(), " H: " + hsv[0] + "\u00B0" + " S: " + hsv[1] + "% " + " V: " + hsv[2] + "%", Toast.LENGTH_SHORT).show();

    }

    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(this.getApplication(), " H: " + hsvmodel.getHue() + "\u00B0" + " S: " + hsvmodel.getSaturation() + "% " + " V: " + hsvmodel.getValue() + "%", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        mAboutDialog.show(getFragmentManager(), ABOUT_DIALOG_TAG);
        return super.onOptionsItemSelected(item);
    }
}
