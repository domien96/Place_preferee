package preferee.GUI;

/**
 * Lijst van alle vensternamen.
 * (Voorkomt typfouten)
 * Created by Domien on 6/05/2015.
 */
public enum ScreenName {
    Main, // start- / hoofdvenster
    TestProviderSetup, // setup van testprov
    ProdProviderSetup, // setup van prod prov
    Home, // Venster na de setup
    ListMovies, // Lijst(Tableview) van alle films
    MovieInfo, // Info over iflm gekozen in (@code ListMovies)
    MovieShowings, // voorstellingen van een specifieke film
    Seats, // zitjes kiezen
    Payment, // betalingsformulier van een reservatie
    TransactionComplete; // venster na reservatie-betaling. Hierin staan de kosten en andere nuttige informatie.
}
