'use client'

import * as React from 'react'
import { useState } from 'react'
import { motion, AnimatePresence } from 'framer-motion'
import { useForm } from 'react-hook-form'
import { z } from 'zod'
import { zodResolver } from '@hookform/resolvers/zod'
import { cva, type VariantProps } from "class-variance-authority"
import { Loader2 } from 'lucide-react'
import * as LabelPrimitive from "@radix-ui/react-label"
import { type ClassValue, clsx } from "clsx"
import { twMerge } from "tailwind-merge"

// Utility function
function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs))
}

// Button Component
const buttonVariants = cva(
    "inline-flex items-center justify-center rounded-md text-sm font-medium transition-colors focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:opacity-50 disabled:pointer-events-none ring-offset-background",
    {
        variants: {
            variant: {
                default: "bg-primary text-primary-foreground hover:bg-primary/90",
                destructive: "bg-destructive text-destructive-foreground hover:bg-destructive/90",
                outline: "border border-input hover:bg-accent hover:text-accent-foreground",
                secondary: "bg-secondary text-secondary-foreground hover:bg-secondary/80",
                ghost: "hover:bg-accent hover:text-accent-foreground",
                link: "underline-offset-4 hover:underline text-primary",
            },
            size: {
                default: "h-10 py-2 px-4",
                sm: "h-9 px-3 rounded-md",
                lg: "h-11 px-8 rounded-md",
            },
        },
        defaultVariants: {
            variant: "default",
            size: "default",
        },
    }
)

interface ButtonProps
    extends React.ButtonHTMLAttributes<HTMLButtonElement>,
        VariantProps<typeof buttonVariants> {
    asChild?: boolean
}

const Button = React.forwardRef<HTMLButtonElement, ButtonProps>(
    ({ className, variant, size, asChild = false, ...props }, ref) => {
        const Comp = asChild ? React.Fragment : "button"
        return (
            <Comp
                className={cn(buttonVariants({ variant, size, className }))}
                ref={ref}
                {...props}
            />
        )
    }
)
Button.displayName = "Button"

// Input Component
interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {}

const Input = React.forwardRef<HTMLInputElement, InputProps>(
    ({ className, type, ...props }, ref) => {
        return (
            <input
                type={type}
                className={cn(
                    "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background file:border-0 file:bg-transparent file:text-sm file:font-medium placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50",
                    className
                )}
                ref={ref}
                {...props}
            />
        )
    }
)
Input.displayName = "Input"

// Label Component
const labelVariants = cva(
    "text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
)

const Label = React.forwardRef<
    React.ElementRef<typeof LabelPrimitive.Root>,
    React.ComponentPropsWithoutRef<typeof LabelPrimitive.Root> &
    VariantProps<typeof labelVariants>
>(({ className, ...props }, ref) => (
    <LabelPrimitive.Root
        ref={ref}
        className={cn(labelVariants(), className)}
        {...props}
    />
))
Label.displayName = LabelPrimitive.Root.displayName

// Icons Component
const Icons = {
    spinner: Loader2,
    google: (props: any) => (
        <svg role="img" viewBox="0 0 24 24" {...props}>
            <path
                fill="currentColor"
                d="M12.48 10.92v3.28h7.84c-.24 1.84-.853 3.187-1.787 4.133-1.147 1.147-2.933 2.4-6.053 2.4-4.827 0-8.6-3.893-8.6-8.72s3.773-8.72 8.6-8.72c2.6 0 4.507 1.027 5.907 2.347l2.307-2.307C18.747 1.44 16.133 0 12.48 0 5.867 0 .307 5.387.307 12s5.56 12 12.173 12c3.573 0 6.267-1.173 8.373-3.36 2.16-2.16 2.84-5.213 2.84-7.667 0-.76-.053-1.467-.173-2.053H12.48z"
            />
        </svg>
    ),
    gitHub: (props: any) => (
        <svg role="img" viewBox="0 0 24 24" {...props}>
            <path
                fill="currentColor"
                d="M12 .297c-6.63 0-12 5.373-12 12 0 5.303 3.438 9.8 8.205 11.385.6.113.82-.258.82-.577 0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 18.07 3.633 17.7 3.633 17.7c-1.087-.744.084-.729.084-.729 1.205.084 1.838 1.236 1.838 1.236 1.07 1.835 2.809 1.305 3.495.998.108-.776.417-1.305.76-1.605-2.665-.3-5.466-1.332-5.466-5.93 0-1.31.465-2.38 1.235-3.22-.135-.303-.54-1.523.105-3.176 0 0 1.005-.322 3.3 1.23.96-.267 1.98-.399 3-.405 1.02.006 2.04.138 3 .405 2.28-1.552 3.285-1.23 3.285-1.23.645 1.653.24 2.873.12 3.176.765.84 1.23 1.91 1.23 3.22 0 4.61-2.805 5.625-5.475 5.92.42.36.81 1.096.81 2.22 0 1.606-.015 2.896-.015 3.286 0 .315.21.69.825.57C20.565 22.092 24 17.592 24 12.297c0-6.627-5.373-12-12-12"
            />
        </svg>
    ),
}

// Form validation schemas
const loginSchema = z.object({
    email: z.string().email('Invalid email address'),
    password: z.string().min(6, 'Password must be at least 6 characters'),
})

const signupSchema = loginSchema.extend({
    name: z.string().min(2, 'Name must be at least 2 characters'),
    confirmPassword: z.string()
}).refine((data) => data.password === data.confirmPassword, {
    message: "Passwords don't match",
    path: ["confirmPassword"],
})

type LoginFormData = z.infer<typeof loginSchema>
type SignupFormData = z.infer<typeof signupSchema>

// Main AuthPage Component
export default function AuthPage() {
    const [isLogin, setIsLogin] = useState(true)
    const [isLoading, setIsLoading] = useState(false)

    const loginForm = useForm<LoginFormData>({
        resolver: zodResolver(loginSchema),
    })

    const signupForm = useForm<SignupFormData>({
        resolver: zodResolver(signupSchema),
    })

    const onLoginSubmit = async (data: LoginFormData) => {
        setIsLoading(true)
        // Add your login logic here
        console.log('Login data:', data)
        await new Promise(resolve => setTimeout(resolve, 1000)) // Simulate API call
        setIsLoading(false)
    }

    const onSignupSubmit = async (data: SignupFormData) => {
        setIsLoading(true)
        // Add your signup logic here
        console.log('Signup data:', data)
        await new Promise(resolve => setTimeout(resolve, 1000)) // Simulate API call
        setIsLoading(false)
    }

    return (
        <div className="min-h-screen flex items-center justify-center bg-[#FFF6E9] p-4">
            <motion.div
                initial={{ opacity: 0, y: 20 }}
                animate={{ opacity: 1, y: 0 }}
                className="w-full max-w-md"
            >
                <div className="bg-white rounded-2xl shadow-xl overflow-hidden">
                    {/* Auth Type Toggle */}
                    <div className="flex text-sm">
                        <button
                            onClick={() => setIsLogin(true)}
                            className={`flex-1 p-4 text-center transition-colors ${
                                isLogin
                                    ? 'bg-[#4335A7] text-white'
                                    : 'bg-[#80C4E9] text-[#4335A7]'
                            }`}
                        >
                            Login
                        </button>
                        <button
                            onClick={() => setIsLogin(false)}
                            className={`flex-1 p-4 text-center transition-colors ${
                                !isLogin
                                    ? 'bg-[#4335A7] text-white'
                                    : 'bg-[#80C4E9] text-[#4335A7]'
                            }`}
                        >
                            Sign Up
                        </button>
                    </div>

                    <div className="p-8">
                        <AnimatePresence mode="wait">
                            {isLogin ? (
                                <motion.form
                                    key="login"
                                    initial={{ opacity: 0, x: -20 }}
                                    animate={{ opacity: 1, x: 0 }}
                                    exit={{ opacity: 0, x: 20 }}
                                    onSubmit={loginForm.handleSubmit(onLoginSubmit)}
                                    className="space-y-4"
                                >
                                    <div className="space-y-2">
                                        <Label htmlFor="email">Email</Label>
                                        <Input
                                            id="email"
                                            type="email"
                                            placeholder="Enter your email"
                                            {...loginForm.register('email')}
                                        />
                                        {loginForm.formState.errors.email && (
                                            <p className="text-sm text-red-500">
                                                {loginForm.formState.errors.email.message}
                                            </p>
                                        )}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="password">Password</Label>
                                        <Input
                                            id="password"
                                            type="password"
                                            placeholder="Enter your password"
                                            {...loginForm.register('password')}
                                        />
                                        {loginForm.formState.errors.password && (
                                            <p className="text-sm text-red-500">
                                                {loginForm.formState.errors.password.message}
                                            </p>
                                        )}
                                    </div>
                                    <Button
                                        type="submit"
                                        className="w-full bg-[#4335A7] hover:bg-[#4335A7]/90"
                                        disabled={isLoading}
                                    >
                                        {isLoading && (
                                            <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                        )}
                                        Login
                                    </Button>
                                </motion.form>
                            ) : (
                                <motion.form
                                    key="signup"
                                    initial={{ opacity: 0, x: 20 }}
                                    animate={{ opacity: 1, x: 0 }}
                                    exit={{ opacity: 0, x: -20 }}
                                    onSubmit={signupForm.handleSubmit(onSignupSubmit)}
                                    className="space-y-4"
                                >
                                    <div className="space-y-2">
                                        <Label htmlFor="name">Name</Label>
                                        <Input
                                            id="name"
                                            type="text"
                                            placeholder="Enter your name"
                                            {...signupForm.register('name')}
                                        />
                                        {signupForm.formState.errors.name && (
                                            <p className="text-sm text-red-500">
                                                {signupForm.formState.errors.name.message}
                                            </p>
                                        )}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="signup-email">Email</Label>
                                        <Input
                                            id="signup-email"
                                            type="email"
                                            placeholder="Enter your email"
                                            {...signupForm.register('email')}
                                        />
                                        {signupForm.formState.errors.email && (
                                            <p className="text-sm text-red-500">
                                                {signupForm.formState.errors.email.message}
                                            </p>
                                        )}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="signup-password">Password</Label>
                                        <Input
                                            id="signup-password"
                                            type="password"
                                            placeholder="Create a password"
                                            {...signupForm.register('password')}
                                        />
                                        {signupForm.formState.errors.password && (
                                            <p className="text-sm text-red-500">
                                                {signupForm.formState.errors.password.message}
                                            </p>
                                        )}
                                    </div>
                                    <div className="space-y-2">
                                        <Label htmlFor="confirm-password">Confirm Password</Label>
                                        <Input
                                            id="confirm-password"
                                            type="password"
                                            placeholder="Confirm your password"
                                            {...signupForm.register('confirmPassword')}
                                        />
                                        {signupForm.formState.errors.confirmPassword && (
                                            <p className="text-sm text-red-500">
                                                {signupForm.formState.errors.confirmPassword.message}
                                            </p>
                                        )}
                                    </div>
                                    <Button
                                        type="submit"
                                        className="w-full bg-[#4335A7] hover:bg-[#4335A7]/90"
                                        disabled={isLoading}
                                    >
                                        {isLoading && (
                                            <Icons.spinner className="mr-2 h-4 w-4 animate-spin" />
                                        )}
                                        Sign Up
                                    </Button>
                                </motion.form>
                            )}
                        </AnimatePresence>

                        <div className="mt-6">
                            <div className="relative">
                                <div className="absolute inset-0 flex items-center">
                                    <div className="w-full border-t border-gray-200" />
                                </div>
                                <div className="relative flex justify-center text-sm">
                                    <span className="bg-white px-2 text-gray-500">Or continue with</span>
                                </div>
                            </div>

                            <div className="mt-6 flex gap-4">
                                <Button
                                    variant="outline"
                                    className="w-full"
                                    onClick={() => console.log('Google login')}
                                >
                                    <Icons.google className="mr-2 h-4 w-4" />
                                    Google
                                </Button>
                                <Button
                                    variant="outline"
                                    className="w-full"
                                    onClick={() => console.log('GitHub login')}
                                >
                                    <Icons.gitHub className="mr-2 h-4 w-4" />
                                    GitHub
                                </Button>
                            </div>
                        </div>
                    </div>
                </div>

                <p className="mt-4 text-center text-sm text-gray-600">
                    {isLogin ? "Don't have an account? " : "Already have an account? "}
                    <button
                        onClick={() => setIsLogin(!isLogin)}
                        className="font-medium text-[#FF7F3E] hover:underline"
                    >
                        {isLogin ? 'Sign up' : 'Login'}
                    </button>
                </p>
            </motion.div>
        </div>
    )
}

