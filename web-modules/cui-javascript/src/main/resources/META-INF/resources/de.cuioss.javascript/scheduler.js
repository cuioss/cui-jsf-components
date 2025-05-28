export function initializeSchedulerLocale(primefaces) {
    if (!primefaces || !primefaces.locales) {
        console.error("PrimeFaces object or PrimeFaces.locales is not defined. Cannot initialize German locale for Scheduler.");
        return;
    }
    primefaces.locales['de'] = {
        closeText: 'Schließen',
        prevText: 'Zurück',
        nextText: 'Weiter',
        monthNames: ['Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'],
        monthNamesShort: ['Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'],
        dayNames: ['Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 'Donnerstag', 'Freitag', 'Samstag'],
        dayNamesShort: ['Son', 'Mon', 'Die', 'Mit', 'Don', 'Fre', 'Sam'],
        dayNamesMin: ['S', 'M', 'D', 'M ', 'D', 'F ', 'S'],
        weekHeader: 'Woche',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: '',
        timeOnlyTitle: 'Nur Zeit',
        timeText: 'Zeit',
        hourText: 'Stunde',
        minuteText: 'Minute',
        secondText: 'Sekunde',
        currentText: 'Aktuelles Datum',
        ampm: false,
        month: 'Monat',
        week: 'Woche',
        day: 'Tag',
        allDayText: 'Ganzer Tag'
    };
}

// For direct browser usage, if PrimeFaces is available on the window
if (typeof window !== 'undefined' && window.PrimeFaces) {
    initializeSchedulerLocale(window.PrimeFaces);
}
