package rdesign.android.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by saravillarreal on 9/7/17.
 */

public class ObjectResponse implements Serializable {

    private ArrayList<Hit> hits;
    private int nbHits;
    private int page;
    private int nbPages;
    private int hitsPerPage;
    private int processingTimeMS;
    private boolean exhaustiveNbHits;
    private String query;
    private String params;

    public ArrayList<Hit> getHits() {
        return hits;
    }

    public void setHits(ArrayList<Hit> hits) {
        this.hits = hits;
    }

    public int getNbHits() {
        return nbHits;
    }

    public void setNbHits(int nbHits) {
        this.nbHits = nbHits;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getNbPages() {
        return nbPages;
    }

    public void setNbPages(int nbPages) {
        this.nbPages = nbPages;
    }

    public int getHitsPerPage() {
        return hitsPerPage;
    }

    public void setHitsPerPage(int hitsPerPage) {
        this.hitsPerPage = hitsPerPage;
    }

    public int getProcessingTimeMS() {
        return processingTimeMS;
    }

    public void setProcessingTimeMS(int processingTimeMS) {
        this.processingTimeMS = processingTimeMS;
    }

    public boolean isExhaustiveNbHits() {
        return exhaustiveNbHits;
    }

    public void setExhaustiveNbHits(boolean exhaustiveNbHits) {
        this.exhaustiveNbHits = exhaustiveNbHits;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
