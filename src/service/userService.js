import axiosInstance from '../axiosConfig';


class UserService{
    static BASE_URL = "http://localhost:8080"

    static async login(cccd, password){
        try{
            const response = await axiosInstance.post(`/auth/login`, {cccd, password})
            return response.data;

        }catch(err){
            throw err;
        }
    }

    // static async register(userData, token){
    //     try{
    //         const response = await axiosInstance.post(`/auth/register`, userData, 
    //         {
    //             headers: {Authorization: `Bearer ${token}`}
    //         })
    //         return response.data;
    //     }catch(err){
    //         throw err;
    //     }
    // }
    // static async register(userData, token) {
    //     if (!token) {
    //         throw new Error('Token is missing');
    //     }
    
    //     try {
    //         const response = await axiosInstance.post(`/auth/register`,
    //             userData,
    //             {
    //                 headers: { Authorization: `Bearer ${token}` },
    //             }
    //         );
    //         return response.data;
    //     } catch (err) {
    //         console.error('Error during registration:', err);
    //         throw err; // Rethrow to be handled in the calling function
    //     }
    // }

    static async register(userData, token) {
        try {
            const headers = token ? { Authorization: `Bearer ${token}` } : {};  // Chỉ thêm token nếu có
            const response = await axiosInstance.post(`/auth/register`, userData, { headers });
            return response.data;
        } catch (err) {
            throw err;
        }
    }
    static async getAllUsers(token){
        try{
            const response = await axiosInstance.get(`/admin/get-all-users`, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }


    static async getYourProfile(token){
        try{
            const response = await axiosInstance.get(`/adminuser/get-profile`, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    static async getUserById(cccd, token){
        try{
            const response = await axiosInstance.get(`/admin/get-users/${cccd}`, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    static async deleteUser(cccd, token){
        try{
            const response = await axiosInstance.delete(`/admin/delete/${cccd}`, 
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }


    static async updateUser(cccd, userData, token){
        try{
            const response = await axiosInstance.put(`/admin/update/${cccd}`, userData,
            {
                headers: {Authorization: `Bearer ${token}`}
            })
            return response.data;
        }catch(err){
            throw err;
        }
    }

    /**AUTHENTICATION CHECKER */
    static logout(){
        localStorage.removeItem('token')
        localStorage.removeItem('role')
    }

    static isAuthenticated(){
        const token = localStorage.getItem('token')
        return !!token
    }

    static isAdmin(){
        const role = localStorage.getItem('role')
        return role === 'ADMIN'
    }

    static isUser(){
        const role = localStorage.getItem('role')
        return role === 'USER'
    }

    static adminOnly(){
        return this.isAuthenticated() && this.isAdmin();
    }

}

export default UserService;