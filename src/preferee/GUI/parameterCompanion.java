package preferee.GUI;

/**
 * Markeer-interface voor alle companions die parameters nodig hebben om aangemaakt te kunnen worden.
 * Companions die deze interface implementeren kunnen niet rechtstreeks aangemaakt worden, maar zullen een "of"-methode bevatten.
 * Hierin worden de parameters meegegeven en wordt een verse companion teruggegeven.
 *
 * EXTRA INFO:
 * In elk van deze soort companions zal er een interne klasse zijn die de parameters bijhoudt.
 * Zo wordt er een mooi onderscheid tussen de vaste waarden en de parameters gehouden.
 * Deze interne klasse wordt in de "of"-methode aangemaakt worden mbv de parameters. Dit object kan dan meegegeven
 * worden aan de (private) constructor van de bijhorende companion.
 *
 * Created by Domien on 4/05/2015.
 */
public interface parameterCompanion {
}
