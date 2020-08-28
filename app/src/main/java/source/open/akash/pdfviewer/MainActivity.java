package source.open.akash.pdfviewer;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.downloader.Error;
import com.downloader.OnDownloadListener;
import com.downloader.PRDownloader;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    Context mContext = MainActivity.this;
    PDFView pdfView;
    ProgressBar progressBar;
    private static String dirPath;
    private String PDFUrl = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        pdfView = findViewById(R.id.pdfView);

        dirPath = Utils.getRootDirPath(mContext);

        PRDownloader.initialize(mContext);

        downloadPdfFromInternet(PDFUrl, dirPath, "dummy.PDF");

    }

    private void downloadPdfFromInternet(String url, final String dirPath, final String fileName) {
        PRDownloader.download(
                url,
                dirPath,
                fileName
        ).build().start(new OnDownloadListener() {
            @Override
            public void onDownloadComplete() {
                File downloadedFile = new File(dirPath, fileName);
                progressBar.setVisibility(View.GONE);
                showPdfFromFile(downloadedFile);
            }

            @Override
            public void onError(Error error) {
                Toast.makeText(mContext,
                        "Error in downloading file : $error",
                        Toast.LENGTH_LONG).show();
            }
        });


        PRDownloader.download(
                url,
                dirPath,
                fileName
        ).build();

    }

    private void showPdfFromFile(File file) {
        pdfView.fromFile(file)
                .password(null)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .load();
    }
}