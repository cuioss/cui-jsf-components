module.exports = function (grunt) {

  // Keep in sync with maven-property 'plugin.resources.cui_css.target'
  const sassOutput = 'target/sass_target/**.*';
  const sass = require('sass');

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
            'target/sass_target/application-default.css': 'src/main/scss/de.cuioss.portal.css/application-default.scss',       // 'destination': 'source'
            'target/sass_target/icons.css': 'src/main/scss/de.cuioss.portal.css/icons.scss',
          }
      }
    }
  });

  grunt.loadNpmTasks('grunt-contrib-clean');
  grunt.loadNpmTasks('grunt-sass');

  // Default task(s).
  grunt.registerTask('default', ['clean', 'sass']);

};