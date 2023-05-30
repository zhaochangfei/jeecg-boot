import {
    axios
} from '@/utils/request'

/**
 * 查询配送单明细
 */
export function queryWmsDistributionDetails(data) {
    return axios({
        url: '/wms/wmsDistributionDetail/list',
        method: 'get',
        params: data
    })
}