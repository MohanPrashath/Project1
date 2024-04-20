package com.mgsofttech.ddmods;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.Progress;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.mgsofttech.ddmods.adapter.AdapterImage;
import com.mgsofttech.ddmods.extras.TimeAgo;
import com.mgsofttech.ddmods.helperClass.HelperCode;
import com.mgsofttech.ddmods.model.ModelOrder;
import com.mgsofttech.ddmods.model.ModelProduct;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;

public class DownloaderActivity extends ActivityBase {
    boolean downloadActive;
    FirebaseAnalytics firebaseAnalytics;
    FirebaseFirestore firebaseFirestore;
    boolean isDeviceMatched;
    MaterialButton itemDownload;
    LinearLayout itemDownloadContent;
    TextView itemDownloadContentPercentage;
    ProgressBar itemDownloadContentProgress;
    TextView itemDownloadContentSizeA;
    TextView itemDownloadContentSizeB;
    SliderView itemImage;
    TextView itemStatDate;
    TextView itemStatFileDownloadStatus;
    LinearLayout itemStatFileLocation;
    TextView itemStatFileLocationHint;
    TextView itemStatFileLocationText;
    TextView itemStatSize;
    TextView itemTitle;
    ModelProduct modelProduct;
    String stringDeviceId;


    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_downloader);
        this.firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        setupBackButton();
        this.firebaseFirestore = FirebaseFirestore.getInstance();
        this.modelProduct = (ModelProduct) getIntent().getSerializableExtra("modelProduct");
        this.stringDeviceId = Settings.Secure.getString(getContentResolver(), "android_id");
        this.itemImage = (SliderView) findViewById(R.id.itemImage);
        this.itemTitle = (TextView) findViewById(R.id.itemTitle);
        this.itemStatSize = (TextView) findViewById(R.id.itemStatSize);
        this.itemStatDate = (TextView) findViewById(R.id.itemStatDate);
        this.itemDownload = (MaterialButton) findViewById(R.id.itemDownload);
        this.itemStatFileDownloadStatus = (TextView) findViewById(R.id.itemStatFileDownloadStatus);
        this.itemStatFileLocationHint = (TextView) findViewById(R.id.itemStatFileLocationHint);
        this.itemStatFileLocationText = (TextView) findViewById(R.id.itemStatFileLocationText);
        this.itemStatFileLocation = (LinearLayout) findViewById(R.id.itemStatFileLocation);
        this.itemDownloadContent = (LinearLayout) findViewById(R.id.itemDownloadContent);
        this.itemDownloadContentProgress = (ProgressBar) findViewById(R.id.itemDownloadContentProgress);
        this.itemDownloadContentPercentage = (TextView) findViewById(R.id.itemDownloadContentPercentage);
        this.itemDownloadContentSizeA = (TextView) findViewById(R.id.itemDownloadContentSizeA);
        this.itemDownloadContentSizeB = (TextView) findViewById(R.id.itemDownloadContentSizeB);
        setupProductInfo();
        checkUserPurchaseDeviceId();
    }

    private void checkUserPurchaseDeviceId() {
        this.firebaseFirestore.collection("Order").whereEqualTo("orderProductId", (Object) this.modelProduct.getId()).whereEqualTo("orderDeviceId", (Object) this.stringDeviceId).limit(1).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            public void onComplete(Task<QuerySnapshot> task) {
                if (!task.isSuccessful() || task.getResult().size() <= 0) {
                    DownloaderActivity.this.setupDownloadCase();
                } else if (((ModelOrder) task.getResult().getDocumentChanges().get(0).getDocument().toObject(ModelOrder.class)).getOrderDeviceId().equals(DownloaderActivity.this.stringDeviceId)) {
                    DownloaderActivity.this.isDeviceMatched = true;
                    DownloaderActivity.this.setupDownloadCase();
                }
            }
        });
    }

    private void setupProductInfo() {
        this.itemTitle.setText(this.modelProduct.getTitle());
        this.itemImage.setSliderAdapter(new AdapterImage(this, this.modelProduct.getListImage()));
        this.itemImage.setIndicatorAnimation(IndicatorAnimationType.WORM);
        this.itemImage.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        this.itemImage.setAutoCycleDirection(2);
        this.itemImage.setIndicatorSelectedColor(-1);
        this.itemImage.setIndicatorUnselectedColor(-7829368);
        this.itemImage.setScrollTimeInSec(4);
        this.itemImage.startAutoCycle();
        this.itemStatSize.setText(this.modelProduct.getSize());
        TextView textView = this.itemStatDate;
        textView.setText("Updated " + TimeAgo.getTimeAgo(this.modelProduct.getTimestamp(), this));
    }

    /* access modifiers changed from: private */
    public void setupDownloadCase() {
        final String str = this.modelProduct.getListLink().get(0);
        final String fileNameFromURL = HelperCode.getFileNameFromURL(str);
        File file = new File(HelperCode.getFolderDownloads() + "/" + fileNameFromURL);
        if (!this.isDeviceMatched) {
            this.itemDownload.setText("Device ID didn't match!");
            this.itemDownload.setEnabled(true);
            this.itemDownload.setVisibility(View.VISIBLE);
            this.itemStatFileLocation.setVisibility(View.GONE);
        } else if (file.exists()) {
            this.itemDownload.setText("Mod Downloaded");
            this.itemDownload.setEnabled(false);
            HelperCode.getFolderDownloads();
            this.itemStatFileLocationText.setText("Download > DD Mods (Folder)");
            this.itemStatFileLocation.setVisibility(View.VISIBLE);
            this.itemDownload.setVisibility(View.GONE);
        } else {
            this.itemDownload.setEnabled(true);
            this.itemDownload.setText("Download Now");
            this.itemDownload.setVisibility(View.VISIBLE);
        }
        this.itemDownload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (!DownloaderActivity.this.isDeviceMatched) {
                    new MaterialAlertDialogBuilder(DownloaderActivity.this, R.style.ThemeProductDialog).setTitle((CharSequence) "Device ID didn't match").setMessage((CharSequence) "We have noticed that you're not downloading the file from the original device which you used to purchase this product.\n\nDue to security concerns we dont allow downloading from a different device. So kindly use the original device and download the file!").setPositiveButton((CharSequence) "I understand", (DialogInterface.OnClickListener) null).create().show();
                } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (DownloaderActivity.this.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                        DownloaderActivity.this.itemDownloadContent.setVisibility(View.VISIBLE);
                        DownloaderActivity.this.itemDownload.setEnabled(false);
                        PRDownloader.download(str, HelperCode.getFolderDownloads(), fileNameFromURL).build().setOnStartOrResumeListener(new OnStartOrResumeListener() {
                            public void onStartOrResume() {
                                DownloaderActivity.this.downloadActive = true;
                            }
                        }).setOnPauseListener(new OnPauseListener() {
                            public void onPause() {
                                DownloaderActivity.this.downloadActive = false;
                            }
                        }).setOnCancelListener(new OnCancelListener() {
                            public void onCancel() {
                                DownloaderActivity.this.downloadActive = false;
                                DownloaderActivity.this.setResult(-1, new Intent().putExtra("complete", false));
                                DownloaderActivity.this.itemDownload.setEnabled(true);
                                DownloaderActivity.this.itemDownload.setVisibility(View.VISIBLE);
                            }
                        }).setOnProgressListener(new OnProgressListener() {
                            public void onProgress(Progress progress) {
                                long j = (progress.currentBytes * 100) / progress.totalBytes;
                                DownloaderActivity.this.itemDownloadContentProgress.setProgress((int) j);
                                TextView textView = DownloaderActivity.this.itemDownloadContentPercentage;
                                textView.setText(j + "%");
                                DownloaderActivity.this.itemDownloadContentSizeA.setText(HelperCode.getBytesToMBString(progress.currentBytes));
                                DownloaderActivity.this.itemDownloadContentSizeB.setText(HelperCode.getBytesToMBString(progress.totalBytes));
                            }
                        }).start(new OnDownloadListener() {
                            public void onDownloadComplete() {
                                DownloaderActivity.this.downloadActive = false;
                                DownloaderActivity.this.setResult(-1, new Intent().putExtra("complete", true));
                                DownloaderActivity.this.itemDownloadContent.setVisibility(View.GONE);
                                DownloaderActivity.this.itemDownload.setText("Mod Downloaded");
                                DownloaderActivity.this.itemDownload.setEnabled(false);
                                DownloaderActivity.this.itemStatFileDownloadStatus.setText("File Downloaded");
                                HelperCode.getFolderDownloads();
                                DownloaderActivity.this.itemStatFileLocationHint.setVisibility(View.VISIBLE);
                                DownloaderActivity.this.itemStatFileLocationText.setText("Download > DD Mods (Folder)");
                                DownloaderActivity.this.itemStatFileLocation.setVisibility(View.VISIBLE);
                                DownloaderActivity.this.itemDownload.setVisibility(View.GONE);
                            }


                            public void onError(Error error) {
                                DownloaderActivity.this.downloadActive = false;
                                DownloaderActivity.this.itemStatFileDownloadStatus.setText("Download Error");
                                DownloaderActivity.this.itemStatFileLocationHint.setVisibility(View.GONE);
                                TextView textView = DownloaderActivity.this.itemStatFileLocationText;
                                textView.setText("Sorry, download error. Try again!\nIf issue persist, please contact support\n\nMore regarding this error:\n" + error.getServerErrorMessage());
                                DownloaderActivity.this.itemStatFileLocation.setVisibility(View.VISIBLE);
                                DownloaderActivity.this.itemDownload.setText("Download Now");
                                DownloaderActivity.this.itemDownload.setEnabled(true);
                                DownloaderActivity.this.itemDownload.setVisibility(View.VISIBLE);
                            }
                        });
                    } else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            DownloaderActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 123);
                        }
                    }
                }
            }
        });
    }

    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED) {
                this.itemDownload.callOnClick();
            } else {
                Toast.makeText(this, "Storage access denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void onBackPressed() {
        if (this.downloadActive) {
            new MaterialAlertDialogBuilder(this).setTitle((CharSequence) "Cancel downloading").setMessage((CharSequence) "You're about cancel the download process. You may minimize the app while you wait or press cancel to end downloading and go back.").setPositiveButton((CharSequence) "Cancel", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialogInterface, int i) {
                    DownloaderActivity.this.finish();
                }
            }).setNegativeButton((CharSequence) "Stay here", (DialogInterface.OnClickListener) null).create().show();
        } else {
            super.onBackPressed();
        }
    }
}
