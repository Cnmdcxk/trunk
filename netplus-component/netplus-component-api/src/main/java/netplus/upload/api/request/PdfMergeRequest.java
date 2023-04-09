package netplus.upload.api.request;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@Getter
@Setter
public class PdfMergeRequest implements Serializable {
    private List<String> pdfUrlList;
}
