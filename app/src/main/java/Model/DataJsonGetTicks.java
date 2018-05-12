package Model;

public class DataJsonGetTicks {
//        Json parse
//    "O":0.00003075,"H":0.00003075,"L":0.00003075,"C":0.00003075,
//    "V":1862.39863755,"T":"2018-04-09T14:45:00","BV":0.05726875},

//    BV: 13.14752793,          // base volume
//    C: 0.000121,              // close
//    H: 0.00182154,            // high
//    L: 0.0001009,             // low
//    O: 0.00182154,            // open
//    T: "2017-07-16T23:00:00", // timestamp
//    V: 68949.3719684          // 24h volume
    float O , H , L, C, V, BV;
    String T;

    public DataJsonGetTicks(float o, float h, float l, float c, float v, float BV, String t) {
        O = o;
        H = h;
        L = l;
        C = c;
        V = v;
        this.BV = BV;
        T = t;
    }

    public float getO() {
        return O;
    }

    public float getH() {
        return H;
    }

    public float getL() {
        return L;
    }

    public float getC() {
        return C;
    }

    public float getV() {
        return V;
    }

    public float getBV() {
        return BV;
    }

    public String getT() {
        return T;
    }
}
