module.exports = function(grunt) {

    const tscOutput = 'target/tsc/**.js';
    const uglifyOutput = 'target/uglify/';
    const uglifySrc = uglifyOutput + 'cui.js';
    const uglifyMin = uglifyOutput + 'cui.min.js';
    const jasmineSpecs = "src/test/resources/javascript/spec[sS]pec.?(m)js";

    // Project configuration.
    grunt.initConfig({
        pkg: grunt.file.readJSON('package.json'),
        clean: [uglifyOutput, tscOutput],
        ts: {
            default: {
                tsconfig: './tsconfig.json'
            }
        },
        jshint: {
            all: [tscOutput]
        },
        uglify: {
            uncompressed: {
                options: {
                    mangle: false,
                    beautify: {
                        width: 80
                    }
                },
                files: {
                    'target/uglify/cui.js': tscOutput
                }
            }, minified: {
                options: {
                    mangle: true,
                    beautify: false
                },
                files: {
                    'target/uglify/cui.min.js': tscOutput
                }
            }
        },
        jasmine: {
            src: uglifyMin,
            options: {
                specs: 'src/test/resources/javascript/spec/**/*[sS]pec.?(m)js',
                vendor: 'src/test/resources/javascript/spec/lib/**/*.js',
                sandboxArgs: {
                    headless: 'new'
                }
            }
        }
    });

    grunt.loadNpmTasks('grunt-contrib-uglify');
    grunt.loadNpmTasks('grunt-contrib-clean');
    grunt.loadNpmTasks('grunt-contrib-jshint');
    grunt.loadNpmTasks("@nevware21/grunt-ts-plugin");
    grunt.loadNpmTasks('grunt-contrib-jasmine');


    // Default task(s).
    grunt.registerTask('default', ['clean', 'ts', 'jshint', 'uglify']);
    // TODO owo:Aus irgendeinem Grund wird das chromium plugin nicht mehr geladen, daher auskommentiert
    // grunt.registerTask('default', ['clean', 'ts', 'jshint', 'uglify', 'jasmine']);

};