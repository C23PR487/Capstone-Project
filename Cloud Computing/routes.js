const {
  getAllDataHandler,
  getAllDataByPriorityHandler,
  getDataByIdHandler,
  addDataHandler,
} = require('./handler');

const routes = [
  {
    method: 'GET',
    path: '/spaces/all',
    handler: getAllDataHandler,
  },
  {
    method: 'GET',
    path: '/spaces',
    handler: getAllDataByPriorityHandler,
  },
  {
    method: 'GET',
    path: '/spaces/{id}/details',
    handler: getDataByIdHandler,
  },
  {
    method: 'POST',
    path: '/spaces',
    handler: addDataHandler,
  },
];

module.exports = routes;
