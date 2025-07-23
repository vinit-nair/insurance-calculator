import axios, { AxiosResponse } from 'axios';
import { InsuranceCalculationRequest, InsuranceCalculationResponse } from '../types/InsuranceTypes';

const API_BASE_URL = process.env.NODE_ENV === 'production' 
  ? '/lloyds-insurance-calculator/api' 
  : 'http://localhost:8080/lloyds-insurance-calculator/api';

// Create axios instance with security defaults
const apiClient = axios.create({
  baseURL: API_BASE_URL,
  timeout: 30000,
  headers: {
    'Content-Type': 'application/json',
    'Accept': 'application/json',
    'X-Requested-With': 'XMLHttpRequest'
  },
  withCredentials: false // No credentials needed for this public API
});

// Request interceptor for security
apiClient.interceptors.request.use(
  (config) => {
    // Add timestamp to prevent caching
    config.params = {
      ...config.params,
      _t: Date.now()
    };
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

// Response interceptor for error handling
apiClient.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    if (error.response) {
      // Server responded with error status
      console.error('API Error:', error.response.status, error.response.data);
    } else if (error.request) {
      // Request was made but no response received
      console.error('Network Error:', error.request);
    } else {
      // Something else happened
      console.error('Error:', error.message);
    }
    return Promise.reject(error);
  }
);

export class InsuranceService {
  
  static async calculateInsurance(request: InsuranceCalculationRequest): Promise<InsuranceCalculationResponse> {
    try {
      const response: AxiosResponse<InsuranceCalculationResponse> = await apiClient.post(
        '/insurance/calculate',
        request
      );
      
      return response.data;
    } catch (error: any) {
      if (error.response?.status === 400) {
        throw new Error('Invalid input data. Please check your entries and try again.');
      } else if (error.response?.status === 500) {
        throw new Error('Service temporarily unavailable. Please try again later.');
      } else if (error.code === 'ECONNABORTED') {
        throw new Error('Request timeout. Please check your connection and try again.');
      } else {
        throw new Error('Unable to calculate insurance. Please try again later.');
      }
    }
  }

  static async healthCheck(): Promise<boolean> {
    try {
      const response = await apiClient.get('/insurance/health');
      return response.status === 200;
    } catch (error) {
      return false;
    }
  }


}

export default InsuranceService; 