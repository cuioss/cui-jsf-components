const path = require('path');
const TerserPlugin = require('terser-webpack-plugin');

module.exports = {
  mode: 'development',
  devtool: 'source-map',
  entry: {
    'cui': './src/main/resources/META-INF/resources/de.cuioss.javascript/cui.js',
    'cui.min': './src/main/resources/META-INF/resources/de.cuioss.javascript/cui.js',
  },
  output: {
    path: path.resolve(__dirname, 'target/uglify'),
    filename: '[name].js', // This will output cui.js and cui.min.js
    library: 'Cui',
    libraryTarget: 'window',
    // The following line is important for source maps to work correctly in dev tools
    devtoolModuleFilenameTemplate: info => path.resolve(info.absoluteResourcePath).replace(/\\/g, '/'),
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        include: [
          path.resolve(__dirname, 'src/main/resources/META-INF/resources/de.cuioss.javascript/')
        ],
        use: {
          loader: 'babel-loader',
          options: {
            presets: ['@babel/preset-env'],
          },
        },
      },
    ],
  },
  optimization: {
    minimize: true, // This enables the minimizer plugins defined in the 'minimizer' array
    minimizer: [
      new TerserPlugin({
        // Ensure this plugin only applies to the .min.js output
        test: /\.min\.js(\?.*)?$/i,
        terserOptions: {
          format: {
            comments: false, // Remove comments from the minified output
          },
        },
        extractComments: false, // Do not extract comments to a separate file
      }),
    ],
  },
  // No plugins needed for this basic setup, TerserPlugin is handled in optimization.minimizer
  plugins: [], 
};
