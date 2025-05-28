module.exports = {
  testEnvironment: 'jsdom', // To simulate browser environment
  setupFilesAfterEnv: ['./src/test/javascript/jest.setup.js'],
  // You might need to add more configuration later, like transform for ES6+ if not using Babel explicitly for tests
};
