package com.im.test


class User {
    PasswordEncrypterService passwordEncrypterService = new PasswordEncrypterService()
    EmailService emailService = new EmailService()

    String username
    String password
    String firstName
    String lastName
    String gender = "Male"
    BigDecimal balance
    List<Product> purchasedProducts = []
    Boolean isPrivellegedCustomer
    BigDecimal incomePerMonth

    String getFullName() {
        return firstName + " " + lastName
    }

    String displayName() {
        String prefix = (gender == "Male") ? 'Mr' : 'Ms'
        return prefix + fullName
    }

    Boolean isValidPassword(String pwd) {
        Boolean isValid = false
        if (pwd && pwd.length() > 7) {
            isValid = true
        }
        return isValid
    }

    void resetPasswordAndSendEmail() {
        String newPassword = "dummy"
        this.password = encyryptPassword(newPassword)
        emailService.sendCancellationEmail(this, newPassword)

    }

    String encyryptPassword(String pwd) {
        String encryptedPassword
        if (this.isValidPassword(pwd)) {
            encryptedPassword = passwordEncrypterService.encrypt(pwd)
        }
        return encryptedPassword
    }


    String getIncomeGroup() {
        String group
        if (this.incomePerMonth <= 5000) {
            group = "MiddleClass"
        } else if (this.incomePerMonth > 5000 && this.incomePerMonth <= 10000) {
            group = "Lower MiddleClass"
        } else if (this.incomePerMonth > 10000) {
            group = "Higher MiddleClass"
        }
        return group
    }


    void purchase(Product p) {
        if (p) {
            purchasedProducts.add(p)
        }
    }

    void cancelPurchase(Product p) {
        if (p) {
            purchasedProducts.remove(p)
        }
    }

    List<String>getSortedInterestedInCategories(){
        List<String> interestedInCategories = getInterestedInCategories()
        interestedInCategories.sort()
    }

    List<String>getInterestedInCategories(){    //assumed to be a very complex method
        sleep(10000)
    }

}
