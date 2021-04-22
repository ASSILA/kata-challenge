/**
 * Model class
 */
public class Transaction implements Comparable{

    private String txtId;
    private String date;
    private String magasin;
    private String idProduit;
    private int quantite;
    private double prix;

    public Transaction() {}



    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public String getTxtId() {
        return txtId;
    }

    public void setTxtId(String txtId) {
        this.txtId = txtId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMagasin() {
        return magasin;
    }

    public void setMagasin(String magasin) {
        this.magasin = magasin;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }



    @Override
    public int compareTo(Object o) {
        if(o instanceof Transaction)
        {
            Transaction t = (Transaction)o;
            double turnover = this.prix * this.quantite;
            return turnover > ((Transaction) o).prix * ((Transaction) o).quantite ? -1 : turnover < ((Transaction) o).prix * ((Transaction) o).quantite ? 1 : 0;
        }
        else
            return -1;
    }
}