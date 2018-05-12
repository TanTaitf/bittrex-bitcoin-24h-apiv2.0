package Model;

public class MarketSumary {
    // init Market
    String MarketCurrency = "";
    String baseCurrency = "";
    String MarketCurrencyLong = "";
    String BaseCurrencyLong = "";
    String MinTradeSize = "";
    String MarketName = "";
    String Created = "";
    String LogoUrl = "";
    String TimeStam = "";

    // init Summary
    Double High = 0.0;
    Double Low = 0.0;
    Double Volume = 0.0;
    Double Last = 0.0;
    Double BaseVolume = 0.0;
    Double Bid = 0.0;
    Double Ask = 0.0;
    Double OpenBuyOrders = 0.0;
    Double OpenSellOrders = 0.0;
    Double PrevDay = 0.0;

    public MarketSumary(String marketCurrency, String baseCurrency, String marketCurrencyLong, String baseCurrencyLong, String minTradeSize, String marketName, String created, String logoUrl, Double high, Double low, Double volume, Double last, Double baseVolume, Double bid, Double ask, Double openBuyOrders, Double openSellOrders, Double prevDay, String TimeStam) {
        MarketCurrency = marketCurrency;
        this.baseCurrency = baseCurrency;
        MarketCurrencyLong = marketCurrencyLong;
        BaseCurrencyLong = baseCurrencyLong;
        MinTradeSize = minTradeSize;
        MarketName = marketName;
        Created = created;
        LogoUrl = logoUrl;
        High = high;
        Low = low;
        Volume = volume;
        Last = last;
        BaseVolume = baseVolume;
        Bid = bid;
        Ask = ask;
        OpenBuyOrders = openBuyOrders;
        OpenSellOrders = openSellOrders;
        PrevDay = prevDay;
        this.TimeStam = TimeStam;

    }

    public double compareTo(MarketSumary volume) {
        double compareage=(volume.getVolume());
        /* For Ascending order*/
        return this.Volume-compareage;

        /* For Descending order do like this */
        //return compareage-this.studentage;
    }

    public String getTimeStam() {
        return TimeStam;
    }

    public String getMarketCurrency() {
        return MarketCurrency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public String getMarketCurrencyLong() {
        return MarketCurrencyLong;
    }

    public String getBaseCurrencyLong() {
        return BaseCurrencyLong;
    }

    public String getMinTradeSize() {
        return MinTradeSize;
    }

    public String getMarketName() {
        return MarketName;
    }

    public String getCreated() {
        return Created;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public Double getHigh() {
        return High;
    }

    public Double getLow() {
        return Low;
    }

    public Double getVolume() {
        return Volume;
    }

    public Double getLast() {
        return Last;
    }

    public Double getBaseVolume() {
        return BaseVolume;
    }

    public Double getBid() {
        return Bid;
    }

    public Double getAsk() {
        return Ask;
    }

    public Double getOpenBuyOrders() {
        return OpenBuyOrders;
    }

    public Double getOpenSellOrders() {
        return OpenSellOrders;
    }

    public Double getPrevDay() {
        return PrevDay;
    }
}
