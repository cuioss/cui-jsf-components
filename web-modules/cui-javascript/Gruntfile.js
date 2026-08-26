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
                // Pin the jasmine-core version. "latest" (the default) resolved to 7.0.2,
                // which consolidated boot0.js + boot1.js into a single boot.js.
                // grunt-contrib-jasmine 4.0.0 still asks for boot0/boot1, so both came back
                // empty, the generated _SpecRunner.html carried no boot script at all,
                // jasmine never initialised, no spec ran - and because the reporter never
                // signalled completion the task hung forever instead of failing.
                version: '5.9.0',
                specs: 'src/test/resources/javascript/spec/**/*[sS]pec.?(m)js',
                vendor: 'src/test/resources/javascript/spec/lib/**/*.js',

                sandboxArgs: {
                    headless: 'new',
                    args: ['--no-sandbox', '--disable-setuid-sandbox']
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
    grunt.registerTask('default', ['clean', 'ts', 'jshint', 'uglify', 'jasmine']);

};