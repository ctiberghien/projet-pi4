package View;

public interface SizeHexagone {
    // Coordonée des points pour l'affichage des hexagone
    // Les Hexagones font du 100x100 acutellement
    static int[] x = { -50, 0, 50, 50, 0, -50 };
    static int[] y = { -25, -50, -25, 25, 50, 25 };

//    static int[] xleft = { -48, 0, 50, 50, 0, -48 };
  //  static int[] yleft = { -25, -50, -25, 24, 49, 24 };

  //  static int[] xtop = { -50, 0, 50, 50, 0, -50 };
  //  static int[] ytop = { -24, -49, -24, 25, 50, 25 };

    //static int[] xright = { -50, 0, 48, 49, 0, -50 };
   //static int[] yright = { -25, -50, -25, 23, 49, 24 };

    //static int[] xDefault = { -50, 0, 50, 50, 0, -50 };
  //  static int[] yDefault = { -25, -50, -25, 25, 50, 25 };

    //static int[] xleftDefault = { -48, 0, 50, 50, 0, -48 };
   // static int[] yleftDefault = { -25, -50, -25, 24, 49, 24 };

   // static int[] xtopDefault = { -50, 0, 50, 50, 0, -50 };
   // static int[] ytopDefault = { -24, -49, -24, 25, 50, 25 };

   // static int[] xrightDefault = { -50, 0, 48, 49, 0, -50 };
//    static int[] yrightDefault = { -25, -50, -25, 23, 49, 24 };

    int[][] x3coord = { x,y };

    // public static Integer HexagoneWidth = (int) (100 * 0.8);
    // public static int HexagoneHeigth = (int) (100 * 0.8);
    public static int[] HexagoneSize = { 100, 100 };

    public static void setSize(double scalaire) {
        for (int i = 0; i < 6; i++) {
            x[i] *= scalaire;
            y[i] *= scalaire;
    //        xleft[i] *= scalaire;
      //      yleft[i] *= scalaire;
        //    xtop[i] *= scalaire;
          //  ytop[i] *= scalaire;
           // xright[i] *= scalaire;
           // yright[i] *= scalaire;
        }
        HexagoneSize[0] *= scalaire;
        HexagoneSize[1] *= scalaire;
    }

   
    public static void copytab(int[] res, int[] t) {
        for (int i = 0; i < t.length; i++) {
            res[i] = t[i];
        }
    }

}
