const {
  getAllDataHandler,
  getFilteredDataHandler,
  getDataByIdHandler,
  addDataHandler,
} = require('./handler');

const routes = [
  {
    method: 'GET',
    path: '/stalls/all',
    handler: getAllDataHandler,
  },
  {
    method: 'GET',
    path: '/stalls/filtered',
    handler: getFilteredDataHandler,
  },
  {
    method: 'GET',
    path: '/stalls/{id}/details',
    handler: getDataByIdHandler,
  },
  {
    method: 'POST',
    path: '/stalls',
    handler: addDataHandler,
  },
];

module.exports = routes;
