import { initializeSchedulerLocale } from '../../main/resources/META-INF/resources/de.cuioss.javascript/scheduler.js';

describe('scheduler.js tests', () => {
    beforeEach(() => {
        // Setup PrimeFaces mock before each test to ensure a clean state
        global.PrimeFaces = {
            locales: {}
        };
        // Optionally, delete or change it to ensure the script is doing the work
        // delete global.PrimeFaces.locales['de'];
        // global.PrimeFaces.locales['de'] = { closeText: "Old Value" };
    });

    it('should define PrimeFaces.locales["de"] with correct German translations', () => {
        // Ensure it's not defined by beforeEach or is different
        expect(global.PrimeFaces.locales['de']).toBeUndefined();

        // Initialize the locale
        initializeSchedulerLocale(global.PrimeFaces);

        // Assertions
        expect(global.PrimeFaces.locales['de']).toBeDefined();
        
        const deLocale = global.PrimeFaces.locales['de'];
        expect(deLocale.closeText).toBe('Schließen');
        expect(deLocale.prevText).toBe('Zurück');
        expect(deLocale.nextText).toBe('Weiter');
        
        expect(deLocale.monthNames).toEqual([
            'Januar', 'Februar', 'März', 'April', 'Mai', 'Juni', 
            'Juli', 'August', 'September', 'Oktober', 'November', 'Dezember'
        ]);
        expect(deLocale.monthNamesShort).toEqual([
            'Jan', 'Feb', 'Mär', 'Apr', 'Mai', 'Jun', 
            'Jul', 'Aug', 'Sep', 'Okt', 'Nov', 'Dez'
        ]);
        
        expect(deLocale.dayNames).toEqual([
            'Sonntag', 'Montag', 'Dienstag', 'Mittwoch', 
            'Donnerstag', 'Freitag', 'Samstag'
        ]);
        expect(deLocale.dayNamesShort).toEqual([
            'Son', 'Mon', 'Die', 'Mit', 'Don', 'Fre', 'Sam'
        ]);
        expect(deLocale.dayNamesMin).toEqual([
            'S', 'M', 'D', 'M ', 'D', 'F ', 'S'
        ]);

        expect(deLocale.weekHeader).toBe('Woche');
        expect(deLocale.firstDay).toBe(1);
        expect(deLocale.isRTL).toBe(false);
        expect(deLocale.showMonthAfterYear).toBe(false);
        expect(deLocale.yearSuffix).toBe('');
        expect(deLocale.timeOnlyTitle).toBe('Nur Zeit');
        expect(deLocale.timeText).toBe('Zeit');
        expect(deLocale.hourText).toBe('Stunde');
        expect(deLocale.minuteText).toBe('Minute');
        expect(deLocale.secondText).toBe('Sekunde');
        expect(deLocale.currentText).toBe('Aktuelles Datum');
        expect(deLocale.ampm).toBe(false);
        expect(deLocale.month).toBe('Monat');
        expect(deLocale.week).toBe('Woche');
        expect(deLocale.day).toBe('Tag');
        expect(deLocale.allDayText).toBe('Ganzer Tag');
    });

    it('should overwrite an existing PrimeFaces.locales["de"] object', () => {
        // Setup an existing locale object
        global.PrimeFaces.locales['de'] = {
            closeText: 'Old Close Text',
            prevText: 'Old Prev Text',
            monthNames: ['Old Jan']
        };

        initializeSchedulerLocale(global.PrimeFaces);

        expect(global.PrimeFaces.locales['de']).toBeDefined();
        expect(global.PrimeFaces.locales['de'].closeText).toBe('Schließen'); // New value
        expect(global.PrimeFaces.locales['de'].prevText).toBe('Zurück');   // New value
        expect(global.PrimeFaces.locales['de'].monthNames[0]).toBe('Januar'); // New value
        expect(global.PrimeFaces.locales['de'].monthNames).toHaveLength(12); // Full array
    });
});
