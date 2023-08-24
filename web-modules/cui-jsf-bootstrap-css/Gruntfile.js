module.exports = function (grunt) {

  // Keep in sync with maven-property 'plugin.resources.cui_css.target'
  const sassOutput = 'target/sass_target/**.*';
  const sass = require('node-sass');

  // Project configuration.
  grunt.initConfig({
    pkg: grunt.file.readJSON('package.json'),
    clean: [sassOutput],
    sass: {
      options: {
        implementation: sass,
        sourceMap: true,
        style: 'expanded'
      },
      dist: {
        files: {
          'target/sass_target/application-default.css': 'src/main/scss/de/cuioss/portal/application-default.scss',       // 'destination': 'source'
        }
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-sass');

  // Default task(s).
  grunt.registerTask('default', ['clean', 'sass']);

};