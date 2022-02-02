package taxipark

/*
 * Task #1. Find all the drivers who performed no trips.
 * Водители без поездок
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    allDrivers.filter { driver -> trips.none { it.driver == driver } }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 * Пассажиры с более заданным количеством поездок
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    allPassengers.filter { passenger -> trips.count { passenger in it.passengers} >= minTrips}.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 * Пассажир, который повторял водителя
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    allPassengers.filter { passenger -> trips.count { it.driver == driver && passenger in it.passengers} > 1 }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 * У пассажира больше поездок со скидкой, чем без неё
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    allPassengers.filter { passenger -> (trips.filter { it.discount != null }.count { passenger in it.passengers }) > (trips.filter { it.discount == null }.count { passenger in it.passengers }) }.toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 * Самая частая продолжительность поездки
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return trips.groupBy { it.duration / 10 * 10..it.duration / 10 * 10 +9 }.toList().maxBy { it.second.size }?.first
}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 * Доход 20% водителей больше чем остальной доход
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {
    val summa = trips.sumByDouble { it.cost }
    val bestDrivers = trips.groupBy { it.driver }.map { (_, driver) -> driver.sumByDouble {it.cost} }.sortedDescending().take((0.2*allDrivers.size).toInt()).sum()
    return (trips.isNotEmpty()) && (bestDrivers >= 0.8*summa)
}